import { Label } from "@mui/icons-material"
import { Box,
     FormControl,
     FormLabel, 
     FormControlLabel, 
     RadioGroup, Radio, FormHelperText } from "@mui/material"
import { log } from "console";
import { ChangeEvent, useState } from "react"

function MuiRadioButton() {
    const [value, setValue] = useState('');
    console.log('====================================');
    console.log({value});
    console.log('====================================');
    const handleOnChange = (event:ChangeEvent<HTMLInputElement>) => {
       setValue(event.target.value); 
    }
  return (
    <Box>
        <FormControl error >
            <FormLabel id="job-experience-group-label">
                Years of experience
            </FormLabel >
            <RadioGroup name="job-experience-group" aria-labelledby="job-experience-group-label"
                value={value} 
                onChange={handleOnChange} row>
                    <FormControlLabel control={<Radio size="medium" color="secondary" />} label='0-2' value='0-2' />
                    <FormControlLabel control={<Radio />} label='3-5' value='3-5' />
                    <FormControlLabel control={<Radio />} label='6-10' value='6-10' />
            </RadioGroup>
            <FormHelperText>Invalid selection</FormHelperText>
        </FormControl>
    </Box>
  )
}

export default MuiRadioButton