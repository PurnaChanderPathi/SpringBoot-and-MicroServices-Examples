import { Accordion, AccordionDetails, AccordionSummary, Box, FormControlLabel, Grid2, Radio, RadioGroup, Tab, Tabs, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import axios from 'axios';
import './SPOC.css'
import { useDispatch, useSelector } from 'react-redux';
import { setState } from '../../redux/scoreSlice';

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

const SPOC = ({ GroupNameToSpoc, DivisionToSpoc,selectedUser,setSelectedUser }) => {



    const [value, setValue] = useState(0);
    const [groupName, setGroupName] = React.useState('');
    const [division, setDivision] = React.useState('');
    const [spoc, setSpoc] = useState('');
    const [selectedGroup, setSelectedGroup] = useState('');
    const [selectedDivision, setSelectedDivision] = useState('');
    const [selectedSpoc, setSelectedSpoc] = useState('');
    const token = localStorage.getItem('authToken');
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

     const isActive = useSelector((state) => state.Score.isActive);
     const dispatch = useDispatch();

     useEffect(() => {
            console.log("isAction in SPOC",isActive);   

            if(selectedGroup !== "" && selectedDivision !== "" && selectedSpoc !== ""){
                if(!isActive){
                    dispatch(setState(true));
                }                
            }       
     },[selectedGroup, selectedDivision, selectedSpoc, isActive, dispatch])

     useEffect(() => {
        // if(selectedSpoc){
        //     setSelectedUser(selectedSpoc);
            console.log("SelectedUser in Spoc ",selectedUser);
            
        // }
     },[selectedUser])
 
    useEffect(() => {
        if (GroupNameToSpoc && DivisionToSpoc) {
            setSelectedGroup(GroupNameToSpoc);
            setSelectedDivision(DivisionToSpoc);
            handleGroupchanged(GroupNameToSpoc);
            handleDivisionChanged(DivisionToSpoc);
        }
    }, [GroupNameToSpoc, DivisionToSpoc])


    const handleSpocRadioButtonChange = async (event) => {
        const selectedSpoc = event.target.value;
        setSelectedSpoc(selectedSpoc);
        console.log("onChange Spoc :", selectedSpoc);
        setSelectedUser(selectedSpoc);
        console.log(`GroupName : ${selectedGroup} && Division : ${selectedDivision} && Spoc : ${selectedSpoc}`);

    }

    useEffect(() => {
        const fetchGroupNames = async () => {
            try {
                const response = await axios.get('http://localhost:9195/api/adminConfig/getGroupNames', {
                    headers: {

                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,

                    }
                });
                console.log("GroupNames API response:", response.data);
                if (response.data && Array.isArray(response.data.result)) {
                    setGroupName(response.data.result);
                } else {
                    console.error("Expected array but received:", response.data);
                    setGroupName([]);
                }
            } catch (error) {
                console.error("Error fetching group names", error);
                setGroupName([]);
            }
        };

        fetchGroupNames();
    }, []);

    const handleGroupchanged = async (selectedGroup) => {
        if (selectedGroup) {
            try {
                const response = await axios.get(`http://localhost:9195/api/adminConfig/getDivisions/${selectedGroup}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`,
                    }
                }
                );
                console.log("Divisions API response:", response.data.result);
                if (response.data && Array.isArray(response.data.result)) {
                    setDivision(response.data.result);
                    console.log("division on load",division);
                    
                } else {
                    console.error("Divisions response is not an array", response.data);
                    setDivision([]);
                }
            } catch (error) {
                console.error("Error fetching divisions", error);
                setDivision([]);
            } finally {
            }
        }
    }


    const handleGroupChange = async (event) => {
        const selectedGroup = event.target.value;
        setSelectedGroup(selectedGroup);
        handleGroupchanged(selectedGroup);
        setSelectedDivision('');
        setSpoc('');
    }

    // const handleGroupChange = async (event) => {
    //     const selectedGroup = event.target.value;
    //     setSelectedGroup(selectedGroup);
    //     setSelectedDivision('');
    //     setSpoc('');

    //     if (selectedGroup) {
    //         try {
    //             const response = await axios.get(`http://localhost:9195/api/adminConfig/getDivisions/${selectedGroup}`, {
    //                 headers: {
    //                     'Content-Type': 'application/json',
    //                     'Authorization': `Bearer ${token}`,
    //                 }
    //             }
    //             );
    //             console.log("Divisions API response:", response.data);
    //             if (response.data && Array.isArray(response.data.result)) {
    //                 setDivision(response.data.result)
    //             } else {
    //                 console.error("Divisions response is not an array", response.data);
    //                 setDivision([]);
    //             }
    //         } catch (error) {
    //             console.error("Error fetching divisions", error);
    //             setDivision([]);
    //         } finally {
    //         }
    //     }
    // };

    const handleDivisionChanged = async (selectedDivision) => {
        if (selectedDivision) {
                    try {
                        const response = await axios.get(`http://localhost:9195/api/adminConfig/getSpocs/${selectedDivision}`, {
                            headers: {
                                'Authorization': `Bearer ${token}`,
                                'Content-Type': 'application/json'
                            }
                        });
                        console.log("Spoc API response:", response.data);
                        if (response.data && Array.isArray(response.data.result)) {
                            setSpoc(response.data.result);
                        } else {
                            console.error("Divisions response is not an array", response.data);
                            setSpoc([]);
                        }
        
                    } catch (error) {
                        console.error("Error fetching divisions", error);
                        setSpoc([]);
                    }
                }
    }

    const handleDivisionChange = async (event) => {
        const selectedDivision = event.target.value;
        setSelectedDivision(selectedDivision);
        setSelectedSpoc('');
    }

    // const handleDivisionChange = async (event) => {
    //     const selectedDivision = event.target.value;
    //     setSelectedDivision(selectedDivision);
    //     setSelectedSpoc('');

    //     if (selectedDivision) {
    //         try {
    //             const response = await axios.get(`http://localhost:9195/api/adminConfig/getSpocs/${selectedDivision}`, {
    //                 headers: {
    //                     'Authorization': `Bearer ${token}`,
    //                     'Content-Type': 'application/json'
    //                 }
    //             });
    //             console.log("Spoc API response:", response.data);
    //             if (response.data && Array.isArray(response.data.result)) {
    //                 setSpoc(response.data.result);
    //             } else {
    //                 console.error("Divisions response is not an array", response.data);
    //                 setSpoc([]);
    //             }

    //         } catch (error) {
    //             console.error("Error fetching divisions", error);
    //             setSpoc([]);
    //         }
    //     }
    // }

    return (
        <div className="PlanningStage">
            <Accordion className="PlanningStageDD" sx={{ boxShadow: 'none', border: 'none', }}>
                <AccordionSummary
                    sx={{
                        backgroundColor: 'transparent',
                        color: 'black',
                        padding: '10px',
                        height: '20px',
                        fontSize: '15px',
                        border: '1px solid #B2BEB5',
                        borderRadius: '5px',
                        margin: '10px',
                        '& .MuiAccordionSummary-content': {
                            backgroundColor: 'transparent',
                            borderRadius: '4px',
                            padding: '5px 10px',
                        },
                    }}
                    expandIcon={<ExpandMoreIcon style={{ color: 'FF5E00' }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    <Typography
                        sx={{ fontWeight: 'bold' }}>
                        <span style={{
                            textDecoration: 'underline',
                            textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                            textUnderlineOffset: '4px'
                        }}
                            className='underlineText'>SP</span>OC'S
                    </Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                {/* <Tab
                                    label="CREDIT REVIEWER"
                                    {...a11yProps(0)}
                                    sx={{
                                        bgcolor: value === 0 ? 'transparent' : 'transparent',   
                                        color: value === 0 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px',
                                        '&:hover': {
                                            bgcolor: value === 0 ? 'transparent' : '',
                                            color: value === 0 ? 'black' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'transparent',
                                            color: 'black',
                                            borderBottom: '2px solid #FF5E00',
                                        },
                                    }}
                                /> */}
                            </Tabs>
                        </Box>
                        <CustomTabPanel value={value} index={0} className="CommentsScreenPS">
                            <div className='SPOCScreen' >
                                <div className='SPOCGroupName'>
                                    <div className='SPOCgroupNameTitle'>
                                        <Typography className='SPOCNameStyle' sx={{ fontWeight: 'bold' }}>
                                            GROUP NAME
                                        </Typography>
                                    </div>
                                    <div className='RadioGroupName'>
                                        {groupName.length > 0 ? (
                                            <RadioGroup
                                                aria-label="user"
                                                name="user"
                                                value={selectedGroup}
                                                onChange={handleGroupChange}
                                            >
                                                {groupName.map((user, index) => (
                                                    <FormControlLabel
                                                        key={index}
                                                        value={user}
                                                        control={<Radio sx={{
                                                            color: '#FF5E00',
                                                            '&.Mui-checked': {
                                                                color: '#FF5E00',
                                                            },
                                                        }} />}
                                                        label={user}
                                                    />
                                                ))}
                                            </RadioGroup>
                                        ) : (
                                            <p>Loading...</p>
                                        )}
                                    </div>
                                </div>
                                <div className='SPOCDivision'>
                                    <div className='SPOCgroupNameTitle'>
                                        <Typography className='SPOCNameStyle' sx={{ fontWeight: 'bold' }}>
                                            DIVISION
                                        </Typography>
                                    </div>
                                    <div className='RadioGroupName'>
                                        {division.length > 0 ? (
                                            <RadioGroup
                                                aria-label="user"
                                                name="user"
                                                value={selectedDivision}
                                                onChange={handleDivisionChange}
                                            >
                                                {division.map((user, index) => (
                                                    <FormControlLabel
                                                        key={index}
                                                        value={user}
                                                        control={<Radio sx={{
                                                            color: '#FF5E00',
                                                            '&.Mui-checked': {
                                                                color: '#FF5E00',
                                                            },
                                                        }} />}
                                                        label={user}
                                                    />
                                                ))}
                                            </RadioGroup>
                                        ) : (
                                            <p>Select GroupName</p>
                                        )}
                                    </div>
                                </div>
                                <div className='SPOCName'>
                                    <div className='SPOCgroupNameTitle'>
                                        <Typography className='SPOCNameStyle' sx={{ fontWeight: 'bold' }}>
                                            SPOC
                                        </Typography>
                                    </div>
                                    <div className='RadioGroupName'>
                                        {spoc.length > 0 ? (
                                            <RadioGroup
                                                aria-label="user"
                                                name="user"
                                                value={selectedSpoc}
                                                onChange={handleSpocRadioButtonChange}
                                            >
                                                {spoc.map((user, index) => (
                                                    <FormControlLabel
                                                        key={index}
                                                        value={user}
                                                        control={<Radio sx={{
                                                            color: '#FF5E00',
                                                            '&.Mui-checked': {
                                                                color: '#FF5E00',
                                                            },
                                                        }} />}
                                                        label={user}
                                                    />
                                                ))}
                                            </RadioGroup>
                                        ) : (
                                            <p>Select Division</p>
                                        )}
                                    </div>
                                </div>

                            </div>
                        </CustomTabPanel>
                    </Box>
                </AccordionDetails>
            </Accordion>
            <div className='planningPS'>
            </div>
        </div>
    )
}

export default SPOC
