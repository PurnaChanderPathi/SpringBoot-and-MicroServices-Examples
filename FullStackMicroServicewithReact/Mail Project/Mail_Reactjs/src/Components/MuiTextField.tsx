import { Stack, TextField, InputAdornment } from "@mui/material"
import  { useState } from "react"

export const MuiTextField = () => {
    const [value, setValue] = useState('');
    return (
        <Stack spacing={4}>
            <Stack spacing={2} direction={"row"}>
                <TextField label='Name' variant="outlined" />
                <TextField label='Name' variant="filled" />
                <TextField label='Name' variant="standard" />
            </Stack>
            <Stack spacing={2} direction={"row"}>
                <TextField label='Form Input'
                 required
                value={value}
                onChange={(e) => setValue(e.target.value)} 
                error={!value}
                helperText={!value ? 'Requried': 'Do Not share your password with anyone'}
                />
                <TextField label='password' type="password"
                    helperText="Do not share your password with anyone" disabled />
                <TextField label='Read only' InputProps={{ readOnly: true }} />
            </Stack>
            <Stack spacing={2} direction={"row"}>
                <TextField label='Amount' InputProps={{
                    startAdornment: <InputAdornment position="start">$</InputAdornment>,
                }} />
                <TextField label='Weight' InputProps={{
                    endAdornment: <InputAdornment position="end">kg</InputAdornment>,
                }} />
                <Stack />
            </Stack>
        </Stack>
    )
}
