import { AppBar, Box, Button, Grid, Tab, Tabs, Toolbar, Typography, useMediaQuery, useTheme } from '@mui/material'
import React, { useState } from 'react'
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import DrawerComponent from './DrawerComponent';

const Navbar = ({ links }) => {
    const theme = useTheme();
    console.log(theme);
    const isMatch = useMediaQuery(theme.breakpoints.down('md'));
    console.log(isMatch);
    const [value, setValue] = useState();
    return (
        <AppBar sx={{ backgroundImage: "linear-gradient(0deg, rgba(34,195,112,0.9528186274509804) 0%, rgba(40,63,210,0.9752275910364145) 100%)" }}>
            <Toolbar>
                {isMatch ? <>
                    <Typography>
                        <ShoppingCartCheckoutIcon />
                    </Typography>
                    <DrawerComponent links={links} />
                </> : <Grid container sx={{ placeItems: "center" }}>
                    <Grid item xs={2}>
                        <Typography>
                            <ShoppingCartCheckoutIcon />
                        </Typography>
                    </Grid>
                    <Grid item xs={6}>
                        <Tabs indicatorColor='secondary'
                            textColor="inherit"
                            value={value}
                            onChange={(e, val) => setValue(val)}>
                            {links.map((link, index) => (
                                <Tab key={index} label={link} />
                            ))}
                        </Tabs>
                    </Grid>
                    <Grid item xs={1} />
                    <Grid item xs={3}>
                        <Box display={'flex'}>
                            <Button sx={{ marginLeft: "auto", background: "rgba(34,195,112,0.9528186274509804)" }} variant='contained'>Login</Button>
                            <Button sx={{ marginLeft: 1, background: "rgba(34,195,112,0.9528186274509804)" }} variant='contained'>Signup</Button>
                        </Box>
                    </Grid>
                </Grid>
                }
            </Toolbar>
        </AppBar>
    )
}

export default Navbar
