import { Stack, Rating } from "@mui/material"
import { useState } from "react"
import FavoriteIcon from '@mui/icons-material/Favorite';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';

export const MuiRating = () => {
    const [value, setValue] = useState<number | null>(null); //here give number for readOnly and (highlightSelectedOnly) more in place of right side null
    console.log({value});
    const handleOnChange = (event: React.ChangeEvent<{}>, newValue: number | null) => {
        setValue(newValue);
    }
  return (
    <Stack spacing={2}>
        <Rating value={value} size="large" 
        icon={<FavoriteIcon fontSize="inherit" color="error"  />} 
        emptyIcon={<FavoriteBorderIcon fontSize="inherit" />}
         //readOnly 
         //highlightSelectedOnly
       
        precision={0.5} onChange={handleOnChange} />
    </Stack>
  )
}
