import React from 'react'
import './Header.css'
import Grid from '@mui/material/Grid2';
import { Avatar, Box, Button } from '@mui/material';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';
import { useNavigate } from 'react-router-dom';

function Header() {
  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('authToken');
    navigate("/",{replace: true});
  }
  return (

    <Grid container spacing={3} display={'flex'} justifyContent={'center'} alignItems={'center'} height={'90px'} >
      <Grid size={4}>
      </Grid>
      <Grid size={4} fontSize={'25px'}>
        RaghuKrishna Pvt Ltd
      </Grid>
      <Grid id='button' size={4}>
        {/* <PowerSettingsNewIcon className='LogoffButton'/> */}
        <Button className='LogoutButtonHeader'
          sx={{
            backgroundColor: 'rgb(37, 74, 158)',
            textTransform: 'none',
            color: 'white',
            marginLeft: '50px',
            '&:hover': { backgroundColor: 'rgba(37, 74, 158, 0.8)' }
          }}
          variant='contained' onClick={handleLogout}>Logout</Button>
      </Grid>

    </Grid>

  )
}

export default Header
