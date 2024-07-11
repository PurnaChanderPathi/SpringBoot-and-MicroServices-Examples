import React, { useState } from 'react';
import { AppBar, Button, Grid, Toolbar, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';

const NavbarLogin = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [open, setOpen] = useState(false);
    const isSingleMailPage = location.pathname === '/sendMail';


    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleLogout = () => {
        setOpen(false);
        navigate("/");
    };

    const handlePageToggle = () =>{
      if(isSingleMailPage){
        navigate("/massMail");
      } else {
        navigate("/sendMail");
      }
    }

    // const reDirectPage = () => {
    //     setIsMail(prevIsMail => {
    //         const newIsMail = !prevIsMail;
    //         if (newIsMail) {
    //             navigate("/sendMail");
    //         } else {
    //             navigate("/massMail");
    //         }
    //         return newIsMail;
    //     });
    // };

    const handleNavigate = () => {
      navigate('/folder');
    }

    return (
        <div>
            <AppBar>
                <Toolbar>
                    <Grid container sx={{ placeItems: "center" }}>
                        <Grid item xs={8}></Grid>
                        <Grid item xs={1}>
                            <Button 
                                variant='contained' 
                                color='info' 
                                onClick={handleNavigate}
                            >
                                Folder
                            </Button>
                        </Grid>
                        <Grid item xs={1.2}>
                            <Button 
                                variant='contained' 
                                color='info' 
                                onClick={handlePageToggle}
                            >
                                {isSingleMailPage ? "MassMail" : "SingleMail"}
                            </Button>
                        </Grid>
                        <Grid item xs={1}>
                            <Button 
                                variant='contained' 
                                color='info' 
                                onClick={handleClickOpen}
                            >
                                LogOut
                            </Button>
                        </Grid>
                    </Grid>
                </Toolbar>
            </AppBar>
            <Dialog
                open={open}
                onClose={handleClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description"
            >
                <DialogTitle id="alert-dialog-title">{"Confirm Logout"}</DialogTitle>
                <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                        Are you sure you want to logout?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        No
                    </Button>
                    <Button onClick={handleLogout} color="primary" autoFocus>
                        Yes
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default NavbarLogin;
