import React from 'react'
import { Navigate } from 'react-router-dom';

// const PrivateRoute = ({element, ...rest}) => {
//     const isAuthenticated = sessionStorage.getItem('authToken');
//   return isAuthenticated ? element : <Navigate to="/" replace />  // Redirect to login if not authenticated

// };

const PrivateRoute = ({ element }) => {
  const token = localStorage.getItem('authToken');
  if (!token) {
    return <Navigate to="/" />; // If no token, redirect to login page
  }

  // You can add additional checks to validate the token (e.g., check expiration)
  try {
    const decodedToken = JSON.parse(atob(token.split('.')[1])); // Decode JWT token
    const currentTime = Date.now() / 1000;
    if (decodedToken.exp < currentTime) {
      localStorage.removeItem('authToken'); // Remove expired token
      return <Navigate to="/" />; // Redirect to login
    }
  } catch (e) {
    localStorage.removeItem('authToken');
    return <Navigate to="/" />; // Redirect to login if token decoding fails
  }

  return element; // Return the protected component if token is valid
};

export default PrivateRoute
