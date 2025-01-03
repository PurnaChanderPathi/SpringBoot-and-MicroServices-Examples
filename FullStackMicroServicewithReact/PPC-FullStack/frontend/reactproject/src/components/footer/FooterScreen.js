import { Typography } from '@mui/material'
import React from 'react'
import './FooterScreen.css'
import logo from './logoflag-removebg-preview.png';

const FooterScreen = () => {
  return (
    <div className='footClass'>
        <div className='footerImage'>
        <img src={logo} alt="MASQ Logo" className='MASQ' style={{ width: '50px', height: '40px' }} />
        </div>
        <div className='footerHeading'>
            <Typography>Copyright © 2024 Powered by - Raghu - Eidiko Platforms</Typography>
        </div>
    </div>
  )
}

export default FooterScreen
