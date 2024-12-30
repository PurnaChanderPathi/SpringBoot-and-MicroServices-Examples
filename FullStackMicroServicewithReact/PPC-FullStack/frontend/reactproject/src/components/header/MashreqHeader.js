import React, { useState } from 'react'
import logo from './dollar-removebg-preview.png';
import './MashreqHeader.css'
import logoM from './raghukrishna-removebg-preview (1).png'
import NotificationsIcon from '@mui/icons-material/Notifications';
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import { Avatar, Divider, Drawer, Grid2, IconButton, List, ListItem, ListItemText, Tooltip, Typography } from '@mui/material';
import PowerSettingsNewIcon from '@mui/icons-material/PowerSettingsNew';
import { useNavigate } from 'react-router-dom';
import WidgetsIcon from '@mui/icons-material/Widgets';
import ArrowForwardIcon from '@mui/icons-material/ArrowForward';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';



const MashreqHeader = () => {

  const navigate = useNavigate();
  const [openDrawer, setOpenDrawer] = useState(false);
  const username = localStorage.getItem('username');
  const roles = JSON.parse(localStorage.getItem('userRoles')) || [];

  const handleLogout = () => {
    localStorage.removeItem('authToken');
    navigate("/", { replace: true });
  }

  const toggleDrawer = (open) => {
    setOpenDrawer(open);
  };

  return (
    <div className='MashreqMD'>
      <div className='MSHP'>
        <img src={logo} alt="MASQ Logo" className='MASQ' style={{ width: '100px', height: '60px' }} />
      </div>
      <div className='MSHP1'>
        <img src={logoM} alt="MASQ Logo" className='MASQ1' style={{ width: '250px', height: "120px" }} />
      </div>
      <div className='MSHP1'>
        <div className='MSHP1_1'>
          <NotificationsIcon sx={{ color: '#FF5E00' }} />
        </div>
        <div className='MSHP1_2'>
          <ManageAccountsIcon sx={{ color: '#FF5E00' }} />
          <Typography className='typo1' onClick={() => toggleDrawer(true)}>My Access</Typography>
        </div>
        <div className='MSHP1_3'>
          <Typography className='usernameheader'>{username}</Typography>
          <Avatar sx={{ bgcolor: '#FF5E00', width: 30, height: 30 }}>
            {username.charAt(0)}
          </Avatar>

        </div>
        <div>
          <button className='LogoutButtenH' onClick={handleLogout}>
            <Tooltip title="Logout">
              <PowerSettingsNewIcon className='LogoffButton' />
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
      <Drawer
        anchor="right"
        open={openDrawer}
        // onClose={() => toggleDrawer(false)}
      >
        <div
          role="presentation"
          // onClick={() => toggleDrawer(false)}
          // onKeyDown={() => toggleDrawer(false)}
          style={{ width: '250px' }}
        >
          <List>
            <ListItem button>
              <div className='MyAccess'>
                <div>
                <Typography
                  sx={{ fontWeight: 'bold' }}>
                  <span style={{
                    textDecoration: 'underline',
                    textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                    textUnderlineOffset: '4px'
                  }}
                    className='underlineText'>My</span> Access
                </Typography>
                </div>

                <div >
                  <IconButton style={{ color : '#FF5E00'}} onClick={() => toggleDrawer(false)}>
                  <ArrowForwardIosIcon  />
                  </IconButton>
          
          </div>
              </div>
            </ListItem>
            <Divider />
            <div className='MyAccessRoles'>
              <div className='MyAccessHeading'>
                <div className='MyAccessIcon'>
                  <WidgetsIcon />
                </div>
                <div className='myAccessheading'>
                  <Typography
                    sx={{ fontWeight: 'bold' }}>
                    <span style={{
                      textDecoration: 'underline',
                      textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                      textUnderlineOffset: '4px'
                    }}
                      className='underlineText'>PPC</span>
                  </Typography>
                </div>
              </div>
              <div className='rolesDisplay'>
                    {roles.length > 0 ? (
                      roles.map((role, index) => (
                        <ListItem key={index} button>
                                            <Typography
                    sx={{ fontWeight: 'bold' }}>
                      {role}
                  </Typography>
                        </ListItem>
                      ))
                    ): (
                      <Typography>No roles found</Typography>
                    )}
              </div>
            </div>
            {/* <ListItem button onClick={handleLogout}>
              <ListItemText primary="Logout" />
            </ListItem> */}
          </List>
          <Divider />
        </div>
      </Drawer>
    </div>
  )
}

export default MashreqHeader
