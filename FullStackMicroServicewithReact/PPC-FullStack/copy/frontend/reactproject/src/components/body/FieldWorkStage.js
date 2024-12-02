import { Accordion, AccordionDetails, AccordionSummary, Box, Button, Tab, Tabs } from '@mui/material';
import React, { useState } from 'react'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import NoteAddIcon from '@mui/icons-material/NoteAdd';
import './FieldWorkStage.css'

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

const FieldWorkStage = () => {

    const [value, setValue] = useState(0);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

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
                    Field Work Stage
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                <Tab
                                    label="Field Work Stage"
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
                            <div className='fieldworkMD'>
                                <div className='ObligorPage'>
                                    <div>
                                        <Button className='obButton' variant='contained' startIcon={<NoteAddIcon />}>
                                            ADD NEW OBLIGOR
                                        </Button>
                                    </div>
                                </div>
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

export default FieldWorkStage
