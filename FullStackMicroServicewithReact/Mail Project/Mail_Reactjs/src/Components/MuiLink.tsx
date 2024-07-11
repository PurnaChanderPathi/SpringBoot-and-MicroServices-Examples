import { Stack, Link, Typography } from "@mui/material"

function MuiLink() {
    return (
        <Stack spacing={2} direction='row' m={4}>
            <Link href="#" variant="body2">
                Link
            </Link>
            <Typography variant="h6">
            <Link href="#" color='secondary' underline="hover">
                Secondory
            </Link>
            </Typography>
        </Stack>
    )
}

export default MuiLink