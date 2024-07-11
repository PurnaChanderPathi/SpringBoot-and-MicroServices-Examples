import { Stack, Autocomplete, TextField } from "@mui/material"
import { useState } from "react";

type Skill = {
    id: number
    label: string
}

const skills = ['HTML', 'JavaScript', 'CSS', 'TypeScript', 'ReactJs'];

const optionSkills = skills.map((skill, index) => ( {
    id: index + 1,
    label: skill,
}))

export const MuiAutoComplete = () => {
    const [value, setValue] = useState<string | null>(null);
    const [skill, setSkill] = useState<Skill | null>(null);
    console.log({skill});
  return (
    <Stack spacing={3} width='320px'>
            <Autocomplete
             options={skills} 
            renderInput={(params) => <TextField {...params} label='Skills' />}
            value={value} 
            onChange={(event: any, newValue: string | null) => setValue(newValue)} 
           // freeSolo // To write your option we use freeSolo
            />

            <Autocomplete
             options={optionSkills} 
            renderInput={(params) => <TextField {...params} label='Skills' />}
            value={skill} 
            onChange={(event: any, newValue: Skill | null) => setSkill(newValue)} 
            />
    </Stack>
  )
}
