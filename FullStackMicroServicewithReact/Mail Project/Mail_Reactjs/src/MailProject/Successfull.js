import React from 'react';
import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';
import { Box, Grid, Typography } from '@mui/material';
import { useLocation } from 'react-router-dom';

// Define keyframes for the animation
const fadeIn = keyframes`
  from {
    opacity: 0;
    transform: scale(0.9);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
`;

// Create a styled component that uses the keyframes
const AnimatedTypography = styled(Typography)`
  animation: ${fadeIn} 1s ease-in-out;
`;

const Successfull = () => {
    const location = useLocation();
    const { successMessage } = location.state || {};
    console.log("successMessage:", successMessage);

    return (
        <Box display={'flex'} flexDirection={'column'} alignItems={'center'} justifyContent={'center'} height={'100vh'}>
            <Grid
                display={'flex'}
                flexDirection={'column'}
                alignItems={'center'}
                justifyContent={'center'}
                padding={4}
                borderRadius={5}
                boxShadow={"5px 5px 10px #ccc"}
                sx={{
                    ":hover": {
                        boxShadow: '10px 10px 20px #ccc',
                    },
                }}
            >
                <AnimatedTypography variant='h4' gutterBottom>
                {successMessage}
                </AnimatedTypography>
            </Grid>
        </Box>
    );
}

export default Successfull
