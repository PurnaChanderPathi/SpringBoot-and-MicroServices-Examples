import { CheckBox, Label } from '@mui/icons-material'
import { Avatar, Box, Button, Grid, Input, Link, Stack } from '@mui/material'
import React from 'react'
import AccountCircleOutlinedIcon from '@mui/icons-material/AccountCircleOutlined';
import VpnKeyOutlinedIcon from '@mui/icons-material/VpnKeyOutlined';

const LoginForm = () => {
    return (
        <>
            <Box display={'flex'} flexDirection={'row'}  >
                <Grid container justifyContent={'center'} paddingLeft={25} paddingTop={20}>
                    <Grid item xs={5} boxShadow={3} padding={3.5}>
                        <Grid container spacing={2} >
                            <Grid item xs={12}>
                                <label>User Name</label>
                                <Grid container className='nameInput'>
                                    <Grid item className='avatar'><AccountCircleOutlinedIcon /></Grid>
                                    <Grid item> <Input type='text' /></Grid>
                                </Grid>

                            </Grid>
                            <Grid item xs={12}>
                                <label>Password</label>
                                <Grid container className='nameInput'>
                                    <Grid item><VpnKeyOutlinedIcon /></Grid>
                                    <Grid item> <Input type='text' /></Grid>
                                </Grid>
                            </Grid>
                            <Grid item xs={6}>
                                <CheckBox /> <label>Reamind me</label>
                            </Grid>
                            <Grid item xs={6}>
                                <Button>Login</Button>
                            </Grid>
                          
                            
                        </Grid>
                    </Grid>
                    <Grid item xs={5}>
                        <img src='https://t3.ftcdn.net/jpg/02/77/13/70/240_F_277137082_UoYzeK9u2eQp7mE7yI2WyFL68pEKrbdB.jpg' />

                    </Grid>
                   

                </Grid>
                <Stack width={504}>
                </Stack>
            </Box>
            {/* <Box sx={{ flexGrow: 1 }}> */}

            {/* </Box> */}
        </>
    )
}

export default LoginForm
