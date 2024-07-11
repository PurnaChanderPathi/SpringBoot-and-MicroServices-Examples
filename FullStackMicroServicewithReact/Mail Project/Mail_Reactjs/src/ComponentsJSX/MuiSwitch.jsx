import React, { useState } from 'react';
import { Box, Switch, FormControlLabel, FormLabel, FormControl, FormGroup } from '@mui/material';

function MuiSwitch() {
  const [checked, setChecked] = useState(false);
  const [skills, setSkills] = useState([]);
  console.log({ skills });

  const handleSwitch = (event) => {
    setChecked(event.target.checked);
  };

  const handleOnSwitch = (event) => {
    const index = skills.indexOf(event.target.value);
    if (index === -1) {
      setSkills([...skills, event.target.value]);
    } else {
      setSkills(skills.filter((skill) => skill !== event.target.value));
    }
  };

  return (
    <Box>
      <Box>
        <FormControlLabel label='Dark Mode' control={<Switch checked={checked} onChange={handleSwitch} />} />
      </Box>

      <Box>
        <FormControl>
          <FormLabel>Skills</FormLabel>
          <FormGroup>
            <FormControlLabel
              label='HTML'
              control={<Switch value='html' checked={skills.includes('html')} onChange={handleOnSwitch} />}
            />
            <FormControlLabel
              label='CSS'
              control={<Switch value='css' checked={skills.includes('css')} onChange={handleOnSwitch} />}
            />
            <FormControlLabel
              label='JavaScript'
              control={<Switch value='javascript' checked={skills.includes('javascript')} onChange={handleOnSwitch} />}
            />
          </FormGroup>
        </FormControl>
      </Box>
    </Box>
  );
}

export default MuiSwitch;
