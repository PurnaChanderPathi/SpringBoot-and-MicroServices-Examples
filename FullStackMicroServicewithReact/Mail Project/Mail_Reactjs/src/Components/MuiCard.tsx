import { Box, Card, CardContent, Typography, CardActions, Button, CardMedia } from "@mui/material"

function MuiCard() {
  return (
    <Box width='300px'>
        <Card>
            <CardMedia  
            component= 'img'
            height='240'
            image="https://source.unsplash.com/random"
            alt="unsplash image"
            />
            <CardContent>
                <Typography gutterBottom variant="h5" component='div'>
                    React
                </Typography>
                <Typography variant="body2" color='text-secondary'>
                API reference docs for the React Typography component. Learn about the props, CSS, and other APIs of this exported module.
                </Typography>
            </CardContent>
            <CardActions>
                <Button size="small">Share</Button>
                <Button size="small">Learn</Button>
            </CardActions>
        </Card>
    </Box>
  )
}

export default MuiCard