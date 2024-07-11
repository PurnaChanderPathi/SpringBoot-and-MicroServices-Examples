import React from 'react'
import { Box, Checkbox, FormControlLabel, FormLabel, FormControl, FormGroup, FormHelperText } from '@mui/material'
import { useState } from 'react'
import BookmarkBorderIcon from '@mui/icons-material/BookmarkBorder';
import BookmarkIcon from '@mui/icons-material/Bookmark';

function MuiCheckBox() {
    const [acceptTnC, setAcceptTnC] = useState(false);

    const [skills, setSkills] =useState<string[]>([]);

    const handleSkillOnChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const index = skills.indexOf(event.target.value);
        if(index === -1)
        {
            setSkills([...skills, event.target.value])
        } else {
            setSkills(skills.filter((skill) => skill !== event.target.value))
        }
    }

    console.log('====================================');
    console.log({skills});
    console.log('====================================');
    const handleChecked = (event: React.ChangeEvent<HTMLInputElement>) => {
            setAcceptTnC(event.target.checked)
    }
    return (
        <Box>
            <Box>
                <FormControlLabel
                    label="I accept Terms and Conditions"
                    control={<Checkbox size='small' color='secondary' checked={acceptTnC} onChange={handleChecked} />} />
            </Box>
            <Box>
                <Checkbox 
                icon={<BookmarkBorderIcon />}
                checkedIcon={<BookmarkIcon />}
                checked={acceptTnC}
                onChange={handleChecked} />
                
            </Box>
            <Box>
                <FormControl>
                    <FormLabel error>Skills</FormLabel>
                    <FormGroup row>
                        <FormControlLabel
                         label='HTML' 
                         control={<Checkbox value='html' checked={skills.includes('html')} onChange={handleSkillOnChange} />} />
                         <FormControlLabel
                         label='CSS' 
                         control={<Checkbox value='Css' checked={skills.includes('Css')} onChange={handleSkillOnChange} />} />
                         <FormControlLabel
                         label='JavaScript' 
                         control={<Checkbox value='javascript' checked={skills.includes('javascript')} onChange={handleSkillOnChange} />}  />
                    </FormGroup>
                    <FormHelperText error>Invalid Selection</FormHelperText>
                </FormControl>
            </Box>
        </Box>
    )
}

export default MuiCheckBox