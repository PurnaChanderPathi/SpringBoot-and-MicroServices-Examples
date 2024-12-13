import React, { useEffect, useState } from 'react'
import './ResponseAndRemediationStage.css'
import { ToastContainer } from 'react-toastify';
import { Accordion, AccordionDetails, AccordionSummary, Box, MenuItem, Tab, Tabs, TextField, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ResponseTable from './ResponseTable';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { getResponseRemediationDetailsByReviewId } from '../../redux/ResponseRemedaitionSlice';

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

const ResponseAndRemediationStage = () => {
    const [value, setValue] = useState(0);
    const token = localStorage.getItem('authToken');
    const [reviewId, setReviewId] = useState(null); 
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [rows, setRows] = React.useState([]);
    const [totalPages, setTotalPages] = React.useState(1);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    useEffect(() => {
        const storedReviewId = localStorage.getItem('reviewId');
        console.log("reviewId in ResponseRemediation Stage", storedReviewId);

        if (storedReviewId) {
            setReviewId(storedReviewId);  
        }

    }, []); 
    // const dispatch = useDispatch();

    // const rows = useSelector((state) => state.Response.rows);
    // const totalPages = useSelector((state) => state.Response.totalPages);
    // const setTotalPages = useSelector((state) => state.Response.setTotalPages);

    // useEffect(() => {
    //     if (reviewId) {
    //         // dispatch(getResponseRemediationDetailsByReviewId(reviewId,token));
    //         // console.log("ResponseRows", rows);
            
    //          getResponseRemediationDetailsByReviewId(reviewId); 
    //     }
    // }, [reviewId,
    //     // token,
    //     //  dispatch
    //     ]); 



    // const getResponseRemediationDetailsByReviewId = async (reviewId) => {
    //     let url = `http://localhost:9195/api/QueryObligor/getResponseByReviewId?reviewId=${reviewId}`;
    //     try {

    //         const response = await axios.get(url, {
    //             headers : {
    //                 'Authorization': `Bearer ${token}`,
    //                 'Content-Type': 'application/json',
    //             }
    //         });

    //         if(response.data.status === 200){
    //             console.log(`Response Details fetched with reviewId : ${reviewId}`,response.data.message);  
    //             const data = response.data.result;
    //             console.log("Response Details Fetched By reviewId",data);
    //             setRows(data);
    //             setTotalPages(Math.ceil(data.length / rowsPerPage));
            
    //         }
    //         else{
    //             console.log(`Failed to Fetch Response Details with reviewId : ${reviewId}`,response.data.message);
    //             setRows([]);
    //             setTotalPages(0);
    //             console.log("Rows after clearing:", rows);
    //         }
            
    //     } catch (error) {
    //         console.log("Error while process Execution",error.message);
            
    //     }
    // }

    return (
        <div className="PlanningStage">
            <ToastContainer />
            <Accordion className="PlanningStageDD" sx={{ boxShadow: 'none', border: 'none' }}>
                <AccordionSummary
                    sx={{
                        backgroundColor: 'transparent',
                        color: 'black',
                        padding: '10px',
                        height: '20px',
                        fontSize: '15px',
                        border: '1px solid #B2BEB5',
                        borderRadius: '5px',
                        '& .MuiAccordionSummary-content': {
                            backgroundColor: 'transparent',
                            borderRadius: '4px',
                            padding: '5px 10px',
                        },
                    }}
                    expandIcon={<ExpandMoreIcon style={{ color: '#FF5E00' }} />}
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
                            className='underlineText'>Res</span>ponse & Remediation Stage
                    </Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                <Tab
                                    // label="Field Work Stage"
                                    {...a11yProps(0)}
                                    sx={{
                                        bgcolor: value === 0 ? 'transparent' : 'transparent',
                                        color: value === 0 ? 'black' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px',
                                        '&:hover': {
                                            bgcolor: value === 0 ? 'transparent' : 'transparent',
                                            color: value === 0 ? 'black' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'transparent',
                                            color: 'black',
                                        },
                                    }}
                                />
                            </Tabs>
                        </Box>
                        <CustomTabPanel value={value} index={0} className="CommentsScreenPS">
                            <div className='DoYouWantToWorkOn'>
                                <TextField
                                    label="Do You Want To Work On"
                                    className='GroupNameText'
                                    id="GroupName"
                                    select

                                    sx={{
                                        width: '300px',
                                        textAlign: 'center',
                                        paddingLeft: '10px',
                                        '& .MuiOutlinedInput-root': {
                                            '& fieldset': {
                                                borderColor: '#FF5E00;',
                                            },
                                            '&:hover fieldset': {
                                                borderColor: '#FF5E00',
                                            },
                                            '&.Mui-focused fieldset': {
                                                borderColor: '#FF5E00',
                                            },
                                        },
                                    }}
                                >
                                    <MenuItem value="PlanningCompleted">Work on child reviews</MenuItem>
                                    <MenuItem value="Planninginprogress">Work on issues</MenuItem>
                                </TextField>
                            </div>
                            <div>
                                <ResponseTable
                                // rows={rows}
                                //   setTotalPages={setTotalPages} 
                                //   totalPages={totalPages}
                                   />
                            </div>
                        </CustomTabPanel>
                    </Box>
                </AccordionDetails>
            </Accordion>
            <div className='planningPS'>
                {/* <Label>Hello </Label> */}
            </div>
        </div>
    )
}

export default ResponseAndRemediationStage
