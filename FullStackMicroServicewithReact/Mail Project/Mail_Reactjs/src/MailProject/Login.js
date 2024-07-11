import { CheckBox, Label } from '@mui/icons-material'
import { Avatar, Box, Button, Grid, Input, Link, Stack, TextField, Typography, colors } from '@mui/material'
import React, { useState } from 'react'
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import HowToRegOutlinedIcon from '@mui/icons-material/HowToRegOutlined';
import axios from 'axios';
import { properties } from './properties';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import { toast } from 'react-toastify';

const LoginForm = () => {
    const[isSignup, setIsSignup] = useState(false);
    const[inputs, setInputs] = useState({
        username:"",
        password:"",
        role:"" 
    })

    const navigate = useNavigate();

    const signUp = async () => {
        try {
            const response = await axios.post(properties.signUpUrl,inputs);
            console.log(response.data);
            if (response.data.status === 201 && response.data.message === 'SignUp successFull') {
                toast.success(response.data.message, {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
                console.log("SignUp Successfull");
                console.log("successMessage:", response.data.message);
                setEmpty();
            }else {
                toast.error(response.data.errorMessage, {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            }
            } catch (error) {
                toast.error("SignUp failed. Please try again.", {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
        }
    }

    const loginDetails = async () => {
        try {
            const response = await axios.post(properties.loginURL,inputs);
            console.log(response.data);
            if(response.data.status===200 && response.data.message==='success'){
                Swal.fire({
                    icon:'success',
                    title:response.data.message,
                    position:'center',
                    timer:'1500',
                    width:'850px !important',
                    showConfirmButton:false
                })
                localStorage.setItem('token',response.data.token)
                 navigate("/sendMail");
                console.log("Login Successfull");
                setEmpty();
            } else if(response.data.status==400 && response.data.errorMessage==='Invalid username or password'){
                    //alert("User Does not exist");
                    toast.error(response.data.errorMessage, {
                        position: "top-right",
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "light",
                    });
                    
            } else{
                console.log("login failed");
                toast.error("Unexpected error during signIn. Please try again.", {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            }
        } catch (error) {
            console.log(error);
        }

        
    }

    //console.log(isSignup);
    const handleChange = (e) =>{
        setInputs((prevState)=>({
            ...prevState,
            [e.target.name] : e.target.value
        }))
    }

    const handleSubmit = (e) =>{
        e.preventDefault();
        if(isSignup){
            signUp();
        }else{
            loginDetails();
        }
        console.log(inputs);
    }

    const resetState = () => {
        setIsSignup(!isSignup);
        setInputs({username:'',password:'',role:''})
    }

    const setEmpty = () => {
        setInputs({username:'',password:'',role:''})
    }
  return (
    <div>
        <form onSubmit={handleSubmit}>
        <Box display="flex" flexDirection={'column'} 
        maxWidth={400} alignItems={'center'} 
        justifyContent={'center'} 
        margin="auto" 
        marginTop={5}
        padding={6}
        borderRadius={5}
        boxShadow={"5px 5px 10px #ccc"}
        
        sx={{
            ":hover":{
                boxShadow:'10px 10px 20px #ccc',
            }
        }}
        >
        <Typography variant='h2' padding={3} textAlign={'center'}>{isSignup ? "Signup":"Login"}</Typography>

        <TextField name='username' 
        value={inputs.username} 
        onChange={handleChange}
        margin='normal'  
        type='email' 
        variant='outlined' 
        placeholder='username' />

        <TextField name='password'
         value={inputs.password}
         onChange={handleChange}
          margin='normal' 
          type='password' 
          variant='outlined' 
          placeholder='Password'/>

{isSignup &&<TextField name='role'
         value={inputs.role} 
         onChange={handleChange}
         margin='normal' 
         type='text' 
         variant='outlined' 
         placeholder='Role' />}

        <Button endIcon={isSignup ?<HowToRegOutlinedIcon/> :<LoginOutlinedIcon/>} type='submit' sx={{marginTop:3, borderRadius:3}}  variant='contained' color='warning'>{isSignup ? "Signup":"Login"}</Button>
        <Button endIcon={isSignup ?<LoginOutlinedIcon/> :<HowToRegOutlinedIcon/>} sx={{marginTop:3, borderRadius:3}} onClick={resetState} >Change To {isSignup ? "Login":"Signup"}</Button>
        </Box>
        </form>
    </div>
  )
}

export default LoginForm
