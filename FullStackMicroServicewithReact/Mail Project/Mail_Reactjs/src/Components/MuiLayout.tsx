import { Box, Stack, Divider, Grid, Paper} from "@mui/material"

function MuiLayout() {
  return (
    <Paper sx={{padding: '32px'}} elevation={4}>

    <Stack 
        sx={{ border: '1px solid' }}
        direction="column-reverse"
        spacing={2}
        divider={<Divider orientation="vertical" flexItem />}>
    <Box 
    sx={{
        backgroundColor: 'primary.main',
        color: 'white',
        height: '100px',
        width: '100px',
        padding: '16px',
        '&:hover': {
            backgroundColor: 'primary.light',
        },
    }}>
        Chandu
    </Box>
    <Box 
        display='flex'
        height='100px'
        width= '100px'
        bgcolor='success.light'
        p={2}

        >

    </Box>
    </Stack>
    <Grid container my={4} rowSpacing={2} columnSpacing={2}>
        <Grid item xs={3} sm={6}>
            <Box bgcolor='primary.light' p={2}>item 1</Box>
        </Grid>
        <Grid item xs={9} sm={6}>
            <Box bgcolor='primary.light' p={2}>item 2</Box>
        </Grid>
        <Grid item xs={9} sm={6}>
            <Box bgcolor='primary.light' p={2}>item 3</Box>
        </Grid>
        <Grid item xs={3} sm={6}>
            <Box bgcolor='primary.light' p={2}>item 4</Box>
        </Grid>
    </Grid>
    </Paper>
  )
}

export default MuiLayout