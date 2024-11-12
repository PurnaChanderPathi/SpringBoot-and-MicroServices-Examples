import React, { useState } from 'react';
import './PlanningStage.css';
import { Accordion, AccordionDetails, AccordionSummary, Box, Tab, Tabs } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import PropTypes from 'prop-types'; // Import PropTypes
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

// Custom Tab Panel Component
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

export default function PlanningStage() {
    const [value, setValue] = useState(0); // Tab value state
    const [comment, setComment] = useState(''); // State for the Quill editor content
    const [theme, setTheme] = useState('snow'); // State for the Quill editor theme

    // Handle tab change
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    // Handle changes in the editor content
    const handleEditorChange = (html) => {
        setComment(html);
    };

    // Handle theme change (You can add a selector later to toggle themes)
    const handleThemeChange = (newTheme) => {
        if (newTheme === 'core') newTheme = null;
        setTheme(newTheme);
    };

    return (
        <div className="PlanningStage">
            <Accordion className="PlanningStageDD">
                <AccordionSummary
                    sx={{
                        backgroundColor: 'rgb(37, 74, 158)', // Blue background for the whole summary
                        color: 'white', // White text color for the summary
                        padding: '10px', // Optional padding for the summary
                        height: '20px',
                        fontSize: '15px',
                        '& .MuiAccordionSummary-content': {
                            backgroundColor: 'rgb(37, 74, 158)', // A slightly darker blue for the text background
                            borderRadius: '4px', // Optional: rounded corners for the background
                            padding: '5px 10px', // Optional: padding for the text background
                        },
                    }}
                    expandIcon={<ExpandMoreIcon style={{ color: 'white' }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    Planning Stage
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                                <Tab
                                    label="Comments"
                                    {...a11yProps(0)}
                                    sx={{
                                        bgcolor: value === 0 ? 'rgb(37, 74, 158)' : 'transparent',
                                        color: value === 0 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px', // Reducing the padding to decrease height
                                        fontSize: '14px', // Adjusting font size if needed
                                        '&:hover': {
                                            bgcolor: value === 0 ? 'rgb(37, 74, 158)' : 'rgba(37, 74, 158, 0.5)',
                                            color: value === 0 ? 'white' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'rgb(37, 74, 158)',
                                            color: 'white',
                                        },
                                    }}
                                />
                                <Tab
                                    label="Audit Trial"
                                    {...a11yProps(1)}
                                    sx={{
                                        bgcolor: value === 1 ? 'rgb(37, 74, 158)' : 'transparent',
                                        color: value === 1 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px', // Reducing the padding to decrease height
                                        fontSize: '14px', // Adjusting font size if needed
                                        '&:hover': {
                                            bgcolor: value === 1 ? 'rgb(37, 74, 158)' : 'rgba(37, 74, 158, 0.5)',
                                            color: value === 1 ? 'white' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'rgb(37, 74, 158)',
                                            color: 'white',
                                        },
                                    }}
                                />
                                <Tab
                                    label="Documents"
                                    {...a11yProps(2)}
                                    sx={{
                                        bgcolor: value === 2 ? 'rgb(37, 74, 158)' : 'transparent',
                                        color: value === 2 ? 'white' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px', // Reducing the padding to decrease height
                                        fontSize: '14px', // Adjusting font size if needed
                                        '&:hover': {
                                            bgcolor: value === 2 ? 'rgb(37, 74, 158)' : 'rgba(37, 74, 158, 0.5)',
                                            color: value === 2 ? 'white' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'rgb(37, 74, 158)',
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
                                <ReactQuill className='ReactQuillPS'
                                    theme={theme}
                                    onChange={handleEditorChange}
                                    value={comment}
                                    // modules={CustomTabPanel.modules}
                                    // formats={CustomTabPanel.formats}
                                    bounds={'.app'}
                                    placeholder="Write your comment here..."
                                />
                                <button className='ButtonAddPS'>Add</button>
                                </div>

                            </div>
                        </CustomTabPanel>
                        <CustomTabPanel value={value} index={1}>
                            Audit trial
                        </CustomTabPanel>
                        <CustomTabPanel value={value} index={2}>
                            Documents
                        </CustomTabPanel>
                    </Box>
                </AccordionDetails>
            </Accordion>
        </div>
    );
}

// Quill modules for the editor
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
        matchVisual: false, // Prevent additional line breaks when pasting HTML
    },
};

// Quill editor formats
CustomTabPanel.formats = [
    'header', 'font', 'size',
    'bold', 'italic', 'underline', 'strike', 'blockquote',
    'list', 'bullet', 'indent',
    'link', 'image', 'video',
];
