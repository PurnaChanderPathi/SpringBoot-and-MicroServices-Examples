import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { Alert, Button, CircularProgress, Input, MenuItem, TextField } from '@mui/material';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import './Tabs.css'
import BasicTable from './Table';
import MultiSearchTable from './MultiSearchTable';
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

CustomTabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function BasicTabs(buttonClicked, setButtonClicked) {
    const [value, setValue] = React.useState(0);
    const [reviewId, setReviewId] = React.useState('');
    const [childReviewId, setChildReviewId] = React.useState('');
    const [groupName, setGroupName] = React.useState('');
    const [division, setDivision] = React.useState('');
    const [selectedGroup, setSelectedGroup] = React.useState('');
    const [selectedDivision, setSelectedDivision] = React.useState('');
    const [loadingGroupNames, setLoadingGroupNames] = React.useState(true);
    const [loadingDivisions, setLoadingDivisions] = React.useState(false);
    const [isDivisionDisabled, setIsDivisionDisabled] = React.useState(true);
    const [fromDate, setFromDate] = React.useState('');
    const [toDate, setToDate] = React.useState('');

    const token = localStorage.getItem('authToken');
    const [searchParams, setSearchParams] = React.useState({
        reviewId: '',
        childReviewId: '',

    });

    const [searchMultiParams, setSearchMultiParams] = React.useState({
        reviewId: '',
        groupName: '',
        division: '',
        fromDate: '',
        toDate: '',
    })

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleSearch = () => {
        setSearchParams({
            reviewId,
            childReviewId,

        });
    };

    // const handleMultiSearch = () => {
    //     setSearchMultiParams({
    //         reviewId,
    //         groupName,
    //         division,
    //         fromDate,
    //         toDate,
    //     })
    // }

    const handleClear = () => {
        setReviewId('');
        setChildReviewId('');
        setSearchParams({
            reviewId: '',
            childReviewId: '',
        });
    };

    const handleMultiClear = () => {
        setReviewId('');
        setGroupName('');
        setDivision('');
        setFromDate('');
        setToDate('');
        setSearchMultiParams({
            reviewId: '',
            groupName: '',
            division: '',
            fromDate: '',
            toDate: '',
        })
    }

    React.useEffect(() => {
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
                setLoadingGroupNames(false);
            } catch (error) {
                console.error("Error fetching group names", error);
                setGroupName([]);
                setLoadingGroupNames(false);
            }
        };

        fetchGroupNames();
    }, []);


    // Handle change in group selection
const handleGroupChange = async (event) => {
    const selectedGroup = event.target.value;
    setSelectedGroup(selectedGroup); // Only store the selected group

    setSelectedDivision(''); // Reset division when group changes
    setIsDivisionDisabled(false); // Enable division dropdown when group is selected

    if (selectedGroup) {
        setLoadingDivisions(true);
        try {
            const response = await axios.get(`http://localhost:9195/api/adminConfig/getDivisions/${selectedGroup}`, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`,
                }
            });
            console.log("Divisions API response:", response.data);
            if (response.data && Array.isArray(response.data.result)) {
                setDivision(response.data.result); // Set divisions based on the selected group
            } else {
                console.error("Divisions response is not an array", response.data);
                setDivision([]);
            }
        } catch (error) {
            console.error("Error fetching divisions", error);
            setDivision([]);
        } finally {
            setLoadingDivisions(false);
        }
    }
};

// Handle change in division selection
const handleDivisionChange = (event) => {
    const selectedDivision = event.target.value;
    setSelectedDivision(selectedDivision); // Only store the selected division
};

// Use the selected group and division when submitting the search form
const handleMultiSearch = () => {
    setSearchMultiParams({
        reviewId,
        groupName: selectedGroup,  // Store only the selected group name
        division: selectedDivision,  // Store only the selected division
        fromDate,
        toDate,
    });
}


    const downloadExcel = async () => {
        // Check if any one of reviewId, division, or groupName is not empty
        if (reviewId !== "" || division !== "" || groupName !== "") {
            const params = {
                selectedGroup: selectedGroup,
                selectedDivision: division,
                reviewId: reviewId,
                fromDate: fromDate,
                toDate: toDate,
            };
            console.log("params", params);

            const ApiToken = localStorage.getItem('authToken');
            console.log("ApiToken", ApiToken);

            try {
                const response = await axios.get('http://localhost:9195/api/query/download-query-details', {
                    params: params,
                    headers: {
                        'Authorization': `Bearer ${ApiToken}`,
                        'Content-Type': 'application/json',
                    },
                    responseType: 'blob'
                });

                console.log('Response Headers:', response.headers);

                const contentDisposition = response.headers['content-disposition'];

                if (contentDisposition) {
                    console.log('contentgg', contentDisposition);

                    const matches = contentDisposition.match(/filename="([^"]+)"/);

                    if (matches && matches[1]) {

                        const filename = matches[1];
                        console.log('Filename:', filename);
                        // Create a URL for the Blob object
                        const blob = response.data;
                        const url = window.URL.createObjectURL(blob);

                        // Create a temporary <a> element to trigger the download
                        const link = document.createElement('a');
                        link.href = url;
                        link.download = filename;  // Set the filename for the download
                        link.click();  // Trigger the download

                        // Clean up the URL object after download
                        window.URL.revokeObjectURL(url);
                    }
                    else {
                        console.error('Filename not found in Content-Disposition header');
                    }
                } else {
                    console.error('Content-Disposition header is missing');
                }
            } catch (error) {
                console.error('Error downloading file:', error);
            }
        } else {
            // Log or show an alert if none of the fields are filled
            // console.error('At least one of reviewId, division, or groupName must be filled to download the Excel file.');
            alert('Please fill at least one of the fields: Review ID, Division, with dates');
        }

    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                    <Tab
                        label="my queue"
                        {...a11yProps(0)}
                        sx={{
                            bgcolor: value === 0 ? '#1B4D3E' : 'transparent',
                            color: value === 0 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1.5,
                            textTransform: 'none',
                            position: 'relative',
                            '&:hover': {
                                bgcolor: value === 0 ? '#1B4D3E' : '#1B4D3E',
                                color: value === 0 ? 'white' : 'black',
                            },
                            '&.Mui-selected': {
                                bgcolor: '#1B4D3E',
                                color: 'white',
                            },
                        }}
                    />
                    <Tab
                        label="search"
                        {...a11yProps(1)}
                        sx={{
                            bgcolor: value === 1 ? '#1B4D3E' : 'transparent',
                            color: value === 1 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1,
                            textTransform: 'none',
                            position: 'relative',
                            '&:hover': {
                                bgcolor: value === 1 ? '#1B4D3E' : '#1B4D3E',
                                color: value === 1 ? 'white' : 'black',
                            },
                            '&.Mui-selected': {
                                bgcolor: '#1B4D3E',
                                color: 'white',
                            },
                        }}
                    />

                </Tabs>
            </Box>
            <div className='borderDiv'>
                <CustomTabPanel value={value} index={0}>
                    <div className='mainDivs'>
                        <div className='ReviewRow'>
                            <label className='ReviewIdLabel'>ReviewID</label>
                            <input
                                className='ReviewIdInput'
                                type='text'
                                value={reviewId}
                                onChange={(e) => setReviewId(e.target.value)} />
                        </div>
                        <div>
                            <label className='ReviewIdLabel'>ChildReviewID</label>
                            <input
                                className='ReviewIdInput'
                                type='text'
                                placeholder=''
                                onChange={(e) => setChildReviewId(e.target.value)} />
                        </div>
                        <div>
                            <button className='SearchButton' onClick={handleSearch}>
                                Search</button>
                        </div>
                        <div>
                            <button className='SearchButton' onClick={handleClear}>Clear</button>
                        </div>
                    </div>
                    <div>
                        <BasicTable searchParams={searchParams} buttonClicked={buttonClicked} setButtonClicked={setButtonClicked} />
                    </div>
                </CustomTabPanel>
                <CustomTabPanel value={value} index={1}>
                    <div className='SearchMainDiv'>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>Group Name</label>
                            <TextField
                                className='GroupNameText'
                                id="GroupName"
                                select
                                value={selectedGroup}
                                onChange={handleGroupChange}
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&:hover fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                    },
                                }}
                            >
                                {loadingGroupNames ? (
                                    <MenuItem value="">
                                        <CircularProgress size={24} />
                                    </MenuItem>
                                ) : (
                                    Array.isArray(groupName) && groupName.length > 0 ? (
                                        groupName.map((group, index) => (
                                            <MenuItem key={index} value={group}>
                                                {group}
                                            </MenuItem>
                                        ))
                                    ) : (
                                        <MenuItem value="">No group names available</MenuItem>
                                    )
                                )}
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>Division</label>
                            <TextField className='DivisionText'
                                id="Division"
                                select
                                value={selectedDivision}
                                onChange={handleDivisionChange}
                                disabled={isDivisionDisabled || loadingDivisions}
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&:hover fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                    },
                                }}

                            >
                                {loadingDivisions ? (
                                    <MenuItem value="" >
                                        <CircularProgress size={34} />
                                    </MenuItem>
                                ) : (
                                    Array.isArray(division) && division.length > 0 ? (
                                        division.map((div, index) => (
                                            <MenuItem key={index} value={div}>
                                                {div}
                                            </MenuItem>
                                        ))
                                    ) : (
                                        <MenuItem value="">No divisions available</MenuItem>
                                    )
                                )}
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>ReviewId</label>
                            <TextField className='ReviewText'
                                id="ReviewId"
                                value={reviewId}
                                onChange={(e) => setReviewId(e.target.value)}
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&:hover fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                    },
                                }}
                            >
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>From Date</label>
                            <TextField className='ReviewText'
                                id="From-Date"
                                type='date'
                                value={fromDate}
                                onChange={(e) => setFromDate(e.target.value)}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&:hover fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                    },
                                }}
                            >
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>To Date</label>
                            <TextField className='ReviewText'
                                id="To-Date"
                                type='date'
                                value={toDate}
                                onChange={(e) => setToDate(e.target.value)}
                                InputLabelProps={{
                                    shrink: true,
                                }}
                                sx={{
                                    '& .MuiOutlinedInput-root': {
                                        '& fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&:hover fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                        '&.Mui-focused fieldset': {
                                            borderColor: '#1B4D3E',
                                        },
                                    },
                                }}
                            >
                            </TextField>
                        </div>
                        <div className='SearchButtons'>
                            <button className='CleanButton' onClick={handleMultiSearch}>Search</button></div>
                        <div className='SearchButtons'>
                            <button className='CleanButton' onClick={handleMultiClear}>Clear</button></div>
                    </div>
                    <div className='DataExport'>
                        <Button
                            className='dataExportButton'
                            startIcon={<FileDownloadIcon />}
                            sx={{
                                backgroundColor: '#1B4D3E',
                                textTransform: 'none',
                                color: 'white',
                                '&:hover': { backgroundColor: '#1B4D3E' }
                            }}
                            onClick={downloadExcel}
                        >
                            Data Export
                        </Button>
                    </div>
                    <div>
                        <MultiSearchTable searchMultiParams={searchMultiParams} />
                    </div>
                </CustomTabPanel>
            </div>

        </Box>
    );
}
