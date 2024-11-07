import React from 'react'
import './Header.css'
import Grid from '@mui/material/Grid2';
import { Avatar, Box, Button } from '@mui/material';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';

function Header() {
  return (
    // <div className='MainDiv'>
    //   <div className='Heading'>
    //     Amlak private limited
    //   </div>
    //   <div className='logoutButton'>
    //     <PowerSettingsNewIcon/>
    //     <Button>Logout</Button>
    //   </div>
    // </div>

    <Grid container spacing={3} display={'flex'} justifyContent={'center'} alignItems={'center'} height={'90px'} >
        <Grid size={4}>
        </Grid>
        <Grid  size={4} fontSize={'25px'}>
        Pathi Purna Chander
        </Grid>
        <Grid id='button' size={4}>
            <PowerSettingsNewIcon/>
            <Button>Logout</Button>
        </Grid>

    </Grid>

  )
}

export default Header
