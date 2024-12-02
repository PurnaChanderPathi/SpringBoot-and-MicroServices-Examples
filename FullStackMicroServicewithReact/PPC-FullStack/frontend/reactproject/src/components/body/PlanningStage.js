import React, { useState } from 'react';
import './PlanningStage.css';
import { Accordion, AccordionDetails, AccordionSummary, Box, Tab, Tabs, TextField, Tooltip, Typography } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import PropTypes from 'prop-types'; // Import PropTypes
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import axios from 'axios'; // Import Axios
import PlanningStageTable from './PlanningStageTable';
import AuditTrail from './AuditTrail';
import Document from './Document';
import { Label } from '@mui/icons-material';


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

CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

// Accessibility props for Tabs
function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function PlanningStage({ documentMesage,fetchData,rows,setRows }) {
    const [value, setValue] = useState(0); 
    const [comment, setComment] = useState('');
    const [theme, setTheme] = useState('snow');
    const [buttonClicked, setButtonClicked] = useState(false);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleEditorChange = (value) => {
        setComment(value);
    };

    const stripHtmlTags = (html) => {
        const doc = new DOMParser().parseFromString(html, 'text/html');
        return doc.body.textContent || "";
    };

    const handleSaveComment = async () => {

        const plainTextComment = stripHtmlTags(comment);
        console.log("Plain text comment before saving:", plainTextComment);

        if (!plainTextComment.trim()) { 
            alert("Please enter a comment before submitting.");
            return;
        }

        try {
            const reviewId = localStorage.getItem("reviewId");
            console.log("LSreviewId",reviewId);

            const username = localStorage.getItem("username");
            console.log("LSusername",username);

            const ApiToken = localStorage.getItem('authToken');
            console.log("ApiToken",ApiToken);

            if (!ApiToken) {
                alert('Authentication token not found');
                return;
            }

            const data = {
                viewComment: plainTextComment,
                reviewId : reviewId,
                commentedBy : username
            };

            const response = await axios.post('http://localhost:9195/api/comment/saveComment', data, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                }
            });
            if (response.status === 200) {
                console.log('Comment saved successfully!');
                setComment('');
                setButtonClicked(true);
            } else {
                console.log('Failed to save comment');
            }
        } catch (error) {
            console.error('Error saving comment:', error);
            alert('An error occurred while saving the comment');
        }
    };

    return (
        <div className="PlanningStage">
            <Accordion className="PlanningStageDD">
                <AccordionSummary
                    sx={{
                        backgroundColor: 'transparent',
                        color: 'black',
                        padding: '10px',
                        height: '20px',
                        fontSize: '18px',
                        border: '1px solid #B2BEB5' ,
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
                     sx={{fontWeight: 'bold'}}>
                               <span style={{ textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                               textUnderlineOffset: '4px'  }} 
                               className='underlineText'>Addi</span>onal System Info
                            </Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                <Tab
                                    label="Comments"
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
                                <Tab
                                    label="Audit Trial"
                                    {...a11yProps(1)}
                                    sx={{
                                        bgcolor: value === 1 ? '#1B4D3E' : 'transparent',
                                        color: value === 1 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px', 
                                        '&:hover': {
                                            bgcolor: value === 1 ? '#1B4D3E' : 'rgba(27, 77, 62, 0.5)',
                                            color: value === 1 ? 'white' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: '#1B4D3E',
                                            color: 'white',
                                        },
                                    }}
                                />
                                <Tab
                                    label="Documents"
                                    {...a11yProps(2)}
                                    sx={{
                                        bgcolor: value === 2 ? '#1B4D3E' : 'transparent',
                                        color: value === 2 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px',
                                        '&:hover': {
                                            bgcolor: value === 2 ? '#1B4D3E' : 'rgba(27, 77, 62, 0.5)',
                                            color: value === 2 ? 'white' : 'black',
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
                            <div className="CommentsScreen">
                                <div className='EnterCommentPS'>
                                    Enter Comment
                                </div>
                                <div className='ReactQuillScreenPS'>
                                    <ReactQuill
                                        className='ReactQuillPS'
                                        theme={theme}
                                        onChange={handleEditorChange}
                                        value={comment}
                                        placeholder="Write your comment here..."
                                    />
                                    <button
                                        className='ButtonAddPS'
                                        onClick={handleSaveComment}
                                    >
                                        Add
                                    </button>
                                </div>
                                <div className='AddButtonPS'>
                                <PlanningStageTable buttonClicked={buttonClicked} setButtonClicked={setButtonClicked} />
                                </div>
                            </div>
                        </CustomTabPanel>
                        <CustomTabPanel value={value} index={1} style={{ border: '1px solid black' }}>
                           <AuditTrail />
                        </CustomTabPanel>
                        <CustomTabPanel value={value} index={2} style={{ border: '1px solid black' }}>
                            <Document documentMesage={documentMesage}
                             fetchData={fetchData }
                             rows={rows}
                             setRows={setRows} />
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

CustomTabPanel.modules = {
    toolbar: [
        [{ 'header': '1' }, { 'header': '2' }, { 'font': [] }],
        [{ size: [] }],
        ['bold', 'italic', 'underline', 'strike', 'blockquote'],
        [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'indent': '-1' }, { 'indent': '+1' }],
        ['link', 'image', 'video'],
        ['clean'],
    ],
    clipboard: {
        matchVisual: false,
    },
};

// Quill editor formats
CustomTabPanel.formats = [
    'header', 'font', 'size',
    'bold', 'italic', 'underline', 'strike', 'blockquote',
    'list', 'bullet', 'indent',
    'link', 'image', 'video',
];
