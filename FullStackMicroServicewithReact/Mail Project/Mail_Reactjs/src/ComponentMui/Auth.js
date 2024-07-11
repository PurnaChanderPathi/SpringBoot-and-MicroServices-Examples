import { Box, Button, TextField, Typography } from '@mui/material'
import React, { useState } from 'react'
import LoginOutlinedIcon from '@mui/icons-material/LoginOutlined';
import HowToRegOutlinedIcon from '@mui/icons-material/HowToRegOutlined';

const Auth = () => {
    const[isSignup, setIsSignup] = useState(false);
    const[inputs, setInputs] = useState({
        name:"",
        email:"",
        password:"",
    })
    //console.log(isSignup);
    const handleChange = (e) =>{
        setInputs((prevState)=>({
            ...prevState,
            [e.target.name] : e.target.value
        }))
    }

    const handleSubmit = (e) =>{
        e.preventDefault();
        console.log(inputs);
    }

    const resetState = () => {
        setIsSignup(!isSignup);
        setInputs({name:'',email:'',password:''})
    }
  return (
    <div>
        <form onSubmit={handleSubmit}>
        <Box display="flex" flexDirection={'column'} 
        maxWidth={400} alignItems={'center'} 
        justifyContent={'center'} 
        margin="auto" 
        marginTop={5}
        padding={3}
        borderRadius={5}
        boxShadow={"5px 5px 10px #ccc"}
        
        sx={{
            ":hover":{
                boxShadow:'10px 10px 20px #ccc',
            }
        }}
        >
        <Typography variant='h2' padding={3} textAlign={'center'}>{isSignup ? "Signup":"Login"}</Typography>

        {isSignup &&<TextField name='name'
         value={inputs.name} 
         onChange={handleChange}
         margin='normal' 
         type='text' 
         variant='outlined' 
         placeholder='Name' />}

        <TextField name='email' 
        value={inputs.email} 
        onChange={handleChange}
        margin='normal'  
        type='email' 
        variant='outlined' 
        placeholder='Email' />

        <TextField name='password'
         value={inputs.password}
         onChange={handleChange}
          margin='normal' 
          type='password' 
          variant='outlined' 
          placeholder='Password'/>

        <Button endIcon={isSignup ?<HowToRegOutlinedIcon/> :<LoginOutlinedIcon/>} type='submit' sx={{marginTop:3, borderRadius:3}}  variant='contained' color='warning'>{isSignup ? "Signup":"Login"}</Button>
        <Button endIcon={isSignup ?<LoginOutlinedIcon/> :<HowToRegOutlinedIcon/>} sx={{marginTop:3, borderRadius:3}} onClick={resetState} >Change To {isSignup ? "Login":"Signup"}</Button>
        </Box>
        </form>
    </div>
  )
}

export default Auth
