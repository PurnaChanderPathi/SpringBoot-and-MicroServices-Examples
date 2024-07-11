import { Stack, Chip, Avatar } from "@mui/material"
import FaceIcon from '@mui/icons-material/Face';
import { useState } from "react";

function MuiChip() {
    const [chips, setChips] = useState(['Chip 1', 'Chip 2', 'Chip 3']);
    const handleDelete = (chipToDelete: string) => {
        setChips((chips) => chips.filter((chip) => chip !== chipToDelete))
    }
  return (

    <Stack direction='row' spacing={1} padding='20px'>
        <Chip label='Chip' color="primary" size="small" icon={<FaceIcon />} />
        <Chip label='outlined' color="secondary" size="small" variant="outlined" avatar={<Avatar>P</Avatar>} />
        <Chip label='Click' color="success" onClick={() => alert('Clicked')}></Chip>
        <Chip label='Delete' color="error"
         onClick={() => alert('Clicked!')}
         onDelete={() => alert('Deleted!')}></Chip>
         {
            chips.map((chip) => (
                <Chip key={chip} label={chip} onDelete={() => handleDelete(chip)} />
            ) )
         }
    </Stack>
  )
}

export default MuiChip