import React, { useState } from 'react';
import './LoginScreen.css';
import loginImage from './ibm_PNG19658.png';
import { useNavigate } from 'react-router-dom';

const LoginScreen = () => {
  const [userCredentials, setUserCredentials] = useState({
    userName: '',
    password: '',
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    const credentials = {
      username: userCredentials.userName,
      password: userCredentials.password,
    };

    try {
      const response = await fetch('http://localhost:1992/auth/token', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials),
      });

      if (!response.ok) {
        throw new Error('Login failed, please check your credentials');
      }
      const token = await response.text();
      localStorage.setItem('authToken', token);

      window.location.href = '/home';

    } catch (err) {
      setError(err.message); 
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className='LoginScreenMainDiv'>
      <div className='HeaderPhotoLS'>
        <div className='HPhoto'>
          <img src={loginImage} alt="IBM Logo" width="110" height="50" />
        </div>
        <div className='HPhotoheading'>
          Workflow Center
        </div>
      </div>
      <div className='LoginScreenHeading'>
        <form onSubmit={handleLogin}>
          <div className='UserInputsLS'>
            <div className='UserNameLS'>
              <label className='UserLabelLS'>User name</label>
              <input
                className='UserNameLS'
                type='text'
                value={userCredentials.userName}
                onChange={(e) => setUserCredentials({ ...userCredentials, userName: e.target.value })}
                required
              />
            </div>
            <div className='PasswordLS'>
              <label className='UserLabelLS'>Password</label>
              <input
                className='PasswordLS'
                type='password'  // Use password type for security
                value={userCredentials.password}
                onChange={(e) => setUserCredentials({ ...userCredentials, password: e.target.value })}
                required
              />
            </div>
          </div>
          <div className='LSButton'>
            <button className='LSLoginButton' type='submit' disabled={loading}>
              {loading ? 'Logging in...' : 'Log in'}
            </button>
          </div>
          {error && <div className="error-message">{error}</div>}
        </form>
      </div>
      <div className='LMP'>
        Licensed Materials - Property of IBM. © Copyright IBM Corporation 2000, 2021.
      </div>
    </div>
  );
};

export default LoginScreen;
