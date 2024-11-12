import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
  const navigate = useNavigate();

  useEffect(() => {
    const authToken = sessionStorage.getItem('authToken');
    if (!authToken) {
      // If there's no authToken, redirect to login
      navigate("/", { replace: true });
    }
  }, [navigate]);

  return (
    <div>
      <h1>Dashboard - Protected Content</h1>
    </div>
  );
}

export default Dashboard;
