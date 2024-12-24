import React, { useState } from 'react';
import './LoginScreen.css';
import loginImage from '../header/raghukrishna-removebg-preview (1).png';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { Timer10 } from '@mui/icons-material';
import ImageMash from './loginMash.PNG'
import { Typography } from '@mui/material';
import CircularIndeterminate from './loadingScreen';


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
      // setLoading(true);
      const token = await response.text();
      localStorage.setItem('authToken', token);
      localStorage.setItem('username',credentials.username);
      await getUser(credentials.username);

      setTimeout(()=> {
        // window.location.href = '/home';
        setLoading(false);
        navigate('/home'); 
      },500);
     
    

      // navigate('/home');

 

    } catch (err) {
      setError(err.message); 
    } finally {
      // setLoading(false);
    }
  };

  const getUser = async (name) => {

    try {
      const response = await axios.get(`http://localhost:1992/auth/getByName/${name}`,{
        headers : {
          'Content-Type': 'application/json',
        }        
      });
      if(response.status === 200){

        if(response.data.result && Array.isArray(response.data.result.roles)){
          localStorage.setItem('userRoles',JSON.stringify([]));
          const roles = response.data.result.roles;
          console.log("Roles",roles);

          localStorage.setItem('userRoles',JSON.stringify(roles));
          console.log("Roles saved in localStorage:", roles);

        }else {
          console.log("Roles not found in the response.");
        }
      }else{
        throw new Error("Failed to fetch user details with name: " + name);
      }  
    } catch (error) {
      console.log("Error While Fetching Data",error.message);
      
    }
    };

  return (
  <>
 { loading ? (
      <CircularIndeterminate/>
  ):(
    <div className='LoginScreenMainDiv'>
      <div className='loginScreenMash'>
      <div className='HeaderPhotoLS'>
        <div className='HPhoto'>
          <img src={loginImage} alt="IBM Logo" width="200" height="190" className='ImageRK' style={{width : '400px'}} />
        </div>
        {/* <div className='HPhotoheading'>
         Login
        </div> */}
      </div>
      <div className='LoginScreenHeading'>
        <form onSubmit={handleLogin}>
          <div className='UserInputsLS'>
            <text className='loginHeadingMash'>Log in to your account</text>
            <div className='UserNameLS'>
              <label className='UserLabelLS'>User name<span className='required-star'>*</span></label>
              <input
                className='UserNameLS'
                type='text'
                value={userCredentials.userName}
                onChange={(e) => setUserCredentials({ ...userCredentials, userName: e.target.value })}
                required 
              />
            </div>
            <div className='PasswordLS'>
              <label className='UserLabelLS'>Password<span className='required-star'>*</span></label>
              <input
                className='PasswordLS'
                type='password' 
                value={userCredentials.password}
                onChange={(e) => setUserCredentials({ ...userCredentials, password: e.target.value })}
                required
              />
            </div>
          </div>
          <div className='LSButton'>
            <button className='LSLoginButton' type='submit'>
              SIGN IN
            </button>
          </div>
          {error && <div className="error-message">{error}</div>}
        </form>
      </div>
      </div>
      
    <div className='ImageDiv'>
      <div className='ImageDivMash'>
      <img src={ImageMash} alt="IBM Logo" width="800" height="400" className='ImageDivMashq' />
      </div>
    </div>
    </div>
  )
}
    </>
        
  );
};

export default LoginScreen;
