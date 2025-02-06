import axios from 'axios';
import Swal from 'sweetalert2';

const axiosInstance = axios.create({
    baseURL: 'http://localhost:9195', // Replace with your API base URL
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add a request interceptor to include the access token in the headers
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('authToken'); // Get access token from localStorage
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
});

// Add a response interceptor to handle 401 errors (token expiration)
axiosInstance.interceptors.response.use(
    (response) => {
        // Pass successful responses through
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        // Handle 401 errors due to token expiration
        if (error.response && error.response.status === 500 && !originalRequest._retry) {
            originalRequest._retry = true; // Prevent infinite retry loop

            const refreshToken = localStorage.getItem('refreshToken'); // Get refresh token
            if (!refreshToken) {
                Swal.fire({
                    icon: 'error',
                    title: 'Session Expired',
                    text: 'Please log in again.',
                });
                localStorage.clear();
                window.location.href = '/login';
                return Promise.reject(error);
            }

            try {
                // Attempt to get a new access token using the refresh token
                const { data } = await axios.post('http://localhost:1992/auth/refresh', null, {
                    headers: { 'Refresh-Token': refreshToken },
                });

                // Save the new access token
                localStorage.setItem('authToken', data.accessToken);

                // Retry the original request with the new access token
                originalRequest.headers['Authorization'] = `Bearer ${data.accessToken}`;
                return axiosInstance(originalRequest);
            } catch (refreshError) {
                // Handle refresh token failure
                Swal.fire({
                    icon: 'error',
                    title: 'Session Expired',
                    text: 'Please log in again.',
                });
                localStorage.clear();
                window.location.href = '/login';
                return Promise.reject(refreshError);
            }
        }

        // Pass other errors through
        return Promise.reject(error);
    }
);

export default axiosInstance;
