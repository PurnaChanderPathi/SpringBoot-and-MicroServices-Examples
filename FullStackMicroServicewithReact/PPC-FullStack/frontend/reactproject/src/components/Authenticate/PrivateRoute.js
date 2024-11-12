import React from 'react'
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({element, ...rest}) => {
    const isAuthenticated = sessionStorage.getItem('authToken');
  return isAuthenticated ? element : <Navigate to="/" replace />  // Redirect to login if not authenticated

};

export default PrivateRoute
