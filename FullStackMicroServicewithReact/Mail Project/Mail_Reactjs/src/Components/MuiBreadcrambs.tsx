import { Box, Breadcrumbs, Link, Typography } from "@mui/material"
import NavigateNextIcon from '@mui/icons-material/NavigateNext';

function MuiBreadcrambs() {
  return (
    <Box>
        <Breadcrumbs aria-label='breadcrambs' 
        separator={<NavigateNextIcon fontSize="small"
        />} maxItems={3} 
        // itemsAfterCollapse={2} // show two at ending  of breadcramb
        itemsBeforeCollapse={2} // show two at starting  of breadcramb
        >
        <Link underline="hover" href='#'>Home</Link>
        <Link underline="hover" href='#'>Catalog</Link>
        <Link underline="hover" href='#'>Accessories</Link>
        <Typography color='text.primary' >Shoes</Typography>
        </Breadcrumbs>
    </Box>
  )
}

export default MuiBreadcrambs