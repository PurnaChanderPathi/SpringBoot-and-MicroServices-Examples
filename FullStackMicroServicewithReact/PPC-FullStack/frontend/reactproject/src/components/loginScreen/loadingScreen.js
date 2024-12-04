import * as React from 'react';
import CircularProgress from '@mui/material/CircularProgress';
import Box from '@mui/material/Box';
import { Typography } from '@mui/material';

export default function CircularIndeterminate() {
  console.log('Rendering CircularIndeterminate...');
  
  return (
    <div className='loadingScreenMain' style={{height:'100vh', width: '100vw',
     display: 'flex', justifyContent: 'center', alignItems: 'center',
     position: 'absolute', 
      top: 0, 
      left: 0, 
      zIndex: 9999,
     }}>
      <div className='loadingScreen' style={{width: '500px', height: '250px',
         display: 'flex', flexDirection: 'column', border: '1px solid #B2BEB5' }}>
      <div className='loadingHeader' style={{ height: '20vh', display: 'flex',
         justifyContent : 'start', alignItems: 'center', borderBottom: '1px solid #B2BEB5', backgroundColor: 'whitesmoke', paddingLeft: "15px"}}>
        <Typography
          sx={{ fontWeight: 'bold' }}>
          <span style={{
            textDecoration: 'underline',
            textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
            textUnderlineOffset: '4px'
          }}
            className='underlineText'>Loa</span>ding ...
        </Typography>
      </div>
      <div className='loader' style={{display: 'flex', justifyContent: 'center', alignItems: 'center', height:'80vh', backgroundColor: 'white'}}>
        <Box sx={{ display: 'flex', color: '#FF5E00' }}>
          <CircularProgress sx={{color: '#FF5E00'}}/>
        </Box>
      </div>
      </div>
    </div>

  );
}