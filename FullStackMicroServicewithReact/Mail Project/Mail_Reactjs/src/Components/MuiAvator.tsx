import { Stack, Avatar, AvatarGroup } from "@mui/material"

function MuiAvator() {
    return (
        <Stack spacing={4}>
            <Stack spacing={2} direction='row' sx={{ display:'flex' ,justifyContent:'flex-end' }}>
                <Avatar sx={{ bgcolor: 'light.primary', display:'flex' ,justifyContent:'flex-end' }}>PC</Avatar>
                {/* <Avatar sx={{ bgcolor: 'light.primary' }}>AA</Avatar> */}
            </Stack>
            <Stack spacing={2} direction='row'>
                <AvatarGroup max={3}>
                    <Avatar sx={{ bgcolor: 'light.primary' }}>PC</Avatar>
                    <Avatar sx={{ bgcolor: 'light.primary' }}>AA</Avatar>
                    <Avatar sx={{ bgcolor: 'light.primary' }}>AS</Avatar>
                    <Avatar sx={{ bgcolor: 'light.primary' }}>BS</Avatar>
                </AvatarGroup>
            </Stack>
            <Stack spacing={2} direction='row'>
                <Avatar variant="square" sx={{ bgcolor: 'light.primary' , width:'48', height: '48' }}>PC</Avatar>
                <Avatar sx={{ bgcolor: 'light.primary', width:'48', height: '48' }}>AA</Avatar>
            </Stack>
        </Stack>
    )
}

export default MuiAvator