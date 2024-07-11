import React, { useState } from "react";
import { Stack, Autocomplete, TextField } from "@mui/material";

const skills = ['HTML', 'JavaScript', 'CSS', 'TypeScript', 'ReactJs'];

const optionSkills = skills.map((skill, index) => ({
  id: index + 1,
  label: skill,
}));

export const MuiAutoComplete = () => {
  const [value, setValue] = useState(null);
  const [skill, setSkill] = useState(null);
  console.log('====================================');
  console.log({skill});
  console.log('====================================');

  return (
    <Stack spacing={3} width='320px'>
      <Autocomplete
        options={skills}
        renderInput={(params) => <TextField {...params} label='Skills' />}
        value={value}
        onChange={(event, newValue) => setValue(newValue)}
        // freeSolo // Uncomment if you want to allow free text input
      />

      <Autocomplete
        options={optionSkills}
        getOptionLabel={(option) => option.label}
        renderInput={(params) => <TextField {...params} label='Skills' />}
        value={skill}
        onChange={(event, newValue) => setSkill(newValue)}
      />
    </Stack>
  );
};
