import { Accordion, AccordionDetails, AccordionSummary, Box, FormControlLabel, Radio, RadioGroup, Tab, Tabs } from '@mui/material';
import React, { useEffect, useState } from 'react'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import { Sync } from '@mui/icons-material';
import axios from 'axios';


function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}


// Accessibility props for Tabs
function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const AssignmentStage = ({data,selectedUser,setSelectedUser}) => {

console.log("data",data);

    
    const [value, setValue] = useState(0);
    // const [selectedUser, setSelectedUser] = useState('');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleRadioChange = (event) => {
        setSelectedUser(event.target.value);
        
    };
    useEffect(() => {
        console.log("selectedUser",selectedUser);
        
    },[selectedUser])

  
    return (
        <div className="PlanningStage">
            <Accordion className="PlanningStageDD">
                <AccordionSummary
                    sx={{
                        backgroundColor: '#1B4D3E',
                        color: 'white',
                        padding: '10px',
                        height: '20px',
                        fontSize: '15px',
                        '& .MuiAccordionSummary-content': {
                            backgroundColor: '#1B4D3E',
                            borderRadius: '4px',
                            padding: '5px 10px',
                        },
                    }}
                    expandIcon={<ExpandMoreIcon style={{ color: 'white' }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    AssignmentStage
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                <Tab
                                    label="Credit Reviewers"
                                    {...a11yProps(0)}
                                    sx={{
                                        bgcolor: value === 0 ? '#1B4D3E' : 'transparent',   
                                        color: value === 0 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px',
                                        '&:hover': {
                                            bgcolor: value === 0 ? '#1B4D3E' : 'rgba(27, 77, 62, 0.5)',
                                            color: value === 0 ? 'white' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: '#1B4D3E',
                                            color: 'white',
                                        },
                                    }}
                                />
                            </Tabs>
                        </Box>
                        <CustomTabPanel value={value} index={0} className="CommentsScreenPS">
                            <div>
                                {data.length > 0 ? (
                                    <RadioGroup
                                        aria-label="user"
                                        name="user"
                                        value={selectedUser}
                                        onChange={handleRadioChange}
                                    >
                                        {data.map((user, index) => (
                                            <FormControlLabel
                                                key={index}
                                                value={user}
                                                control={<Radio />}
                                                label={user}
                                            />
                                        ))}
                                    </RadioGroup>
                                ):(
                                    <p>Loading...</p>
                                )}
                            </div>
                        </CustomTabPanel>
                    </Box>
                </AccordionDetails>
            </Accordion>
            <div className='planningPS'>
                {/* <Label>Hello </Label> */}
            </div>
        </div>
    );
}

export default AssignmentStage
