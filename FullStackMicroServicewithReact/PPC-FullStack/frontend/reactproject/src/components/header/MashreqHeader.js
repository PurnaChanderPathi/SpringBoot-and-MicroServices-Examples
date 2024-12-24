import React from 'react'
import logo from './dollar-removebg-preview.png';
import './MashreqHeader.css'
import logoM from './raghukrishna-removebg-preview (1).png'
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
        <img src={logo} alt="MASQ Logo"  className='MASQ' style={{width : '100px', height : '60px'}} />
        </div>
        <div className='MSHP1'>
        <img src={logoM} alt="MASQ Logo"  className='MASQ1' style={{width: '250px', height: "120px"}} />
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
