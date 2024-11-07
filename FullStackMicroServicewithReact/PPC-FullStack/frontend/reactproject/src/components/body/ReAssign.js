import React from 'react'
import './ReAssign.css'
import CloseIcon from '@mui/icons-material/Close';
import { Button, MenuItem, TextField } from '@mui/material';
import PersonIcon from '@mui/icons-material/Person';

const ReAssign = ({handleCloseAssign}) => {
    const [teamUsers, setTeamUsers] = React.useState('');
    const exitAssignButton = () => {
        handleCloseAssign();
    }
  return (
    <div className='ReAssignMain'>
        <div className='AssignHeading'>
            <div  className='PPCheading'>
                Assign To
                <button className='ExitIconButton' onClick={exitAssignButton}><CloseIcon  className='ExitIcon'/></button>
            </div>
            <div className='AssignInput'>
        <label className='GroupNameLabelPPC'>TeamUsers</label>
                            <TextField 
                            className='ReAssignInput'
                                id="GroupName"
                                select
                                value={teamUsers}
                                onChange={(e) => setTeamUsers(e.target.value)}
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                <MenuItem value="JDBC">Purna</MenuItem>
                                <MenuItem value="JPA">Ramesh</MenuItem>
                                <MenuItem value="Servlet">Anurag</MenuItem>
                            </TextField>
        </div>
            <div className='assignButtondiv'>
            <Button className='assignButton' sx={{backgroundColor : '#C0E6FF', textTransform : 'none', color : 'black'}} startIcon={<PersonIcon/>}>
            assign
            </Button>
            </div>
        </div>


    </div>
  )
}

export default ReAssign
