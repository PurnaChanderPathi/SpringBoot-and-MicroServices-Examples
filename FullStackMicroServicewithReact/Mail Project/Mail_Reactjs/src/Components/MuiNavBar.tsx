import { AppBar, Toolbar, IconButton, Typography, Stack, Button, Menu, MenuItem } from "@mui/material"
import CatchingPokemonIcon from '@mui/icons-material/CatchingPokemon';
import { CatchingPokemon } from "@mui/icons-material";
import React, { useState } from "react";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';

function MuiNavBar() {
    const [anchorEI, setAnchorEI] = useState<null | HTMLElement>(null);
    const open = Boolean(anchorEI);
    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
            setAnchorEI(event.currentTarget);
    }
    const handleClose = () => {
        setAnchorEI(null)
    }
  return (
    <div>
        <AppBar position="static">
            <Toolbar>
                <IconButton size="large" edge='start' color="inherit" aria-label="logo">
                    <CatchingPokemon />
                </IconButton>
                <Typography variant="h6" component='div' sx={{ flexGrow: 1}}>
                    POKIMONAPP
                </Typography>
                <Stack direction="row" spacing={2}>
                <Button color="inherit">Feature</Button>
                <Button color="inherit">Price</Button>
                <Button color="inherit">Login</Button>
                <Button color="inherit" id="resource-button" 
                onClick={handleClick}
                aria-controls={open ? 'resource-menu' : undefined}
                aria-haspopup = 'true'
                aria-expanded={open ? 'true' : undefined}
                endIcon={<KeyboardArrowDownIcon />}
                >Resource</Button>
                <Button color="inherit">About</Button>
                </Stack>
            </Toolbar>
            <Menu id="resource-menu" 
            anchorEl={anchorEI} open={open}
            MenuListProps={{
                'aria-labelledby' : 'resources-button',
            }}
            onClose={handleClose}
            anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'right'
            }}
            transformOrigin={{
                vertical:'top',
                horizontal: 'right'
            }}
            >
                <MenuItem onClick={handleClose} >Blog</MenuItem>
                <MenuItem onClick={handleClose} >Potcast</MenuItem>
            </Menu>
        </AppBar>
    </div>
  )
}

export default MuiNavBar