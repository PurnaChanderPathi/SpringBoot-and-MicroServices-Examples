import React from 'react'
import logo from './MASQ.AE-b739716f.png';
import './MashreqHeader.css'
import logoM from './Mashreq_logo_2022.svg.png'
import NotificationsIcon from '@mui/icons-material/Notifications';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import { Grid2, Tooltip, Typography } from '@mui/material';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';
import { useNavigate } from 'react-router-dom';



const MashreqHeader = () => {

  const navigate = useNavigate();
  const handleLogout = () => {
    localStorage.removeItem('authToken');
    navigate("/",{replace: true});
  }
  return (
    <div className='MashreqMD'>
        <div className='MSHP'>
        <img src={logo} alt="MASQ Logo"  className='MASQ' />
        </div>
        <div className='MSHP1'>
        <img src={logoM} alt="MASQ Logo"  className='MASQ1' />
        </div>
        <div className='MSHP1'>
            <div className='MSHP1_1'>
                <NotificationsIcon sx={{ color: '#FF5E00'}} />
            </div>
            <div className='MSHP1_2'>
                <ManageAccountsIcon  sx={{ color: '#FF5E00'}} />
                <Typography  className='typo1'>My Access</Typography>

            </div>
            <div>
            <button className='LogoutButtenH' onClick={handleLogout}>
            <Tooltip title="Logout">
            <PowerSettingsNewIcon  className='LogoffButton'/>
            </Tooltip>
          </button>
            </div>
            {/* <div>
            <Grid2 id='button' size={4}>
        <div className='LogoffButtonContainer'>
          <button className='LogoutButtenH' onClick={handleLogout}>
            <Tooltip title="Logout">
            <PowerSettingsNewIcon  className='LogoffButton'/>
            </Tooltip>
          </button>
        </div>
      </Grid2>
            </div> */}
        </div>
    </div>
  )
}

export default MashreqHeader
