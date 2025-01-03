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
import MyQueueTable from './MyQueueTable';
import { useSelector } from 'react-redux';
import { toast, ToastContainer } from 'react-toastify';
import Swal from 'sweetalert2';

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
    const [rowData ,setRowData] = React.useState(false);
    const [isRows,setIsRows] = React.useState(false);
    const {isActive} = useSelector((state) => state.Score);

    const token = localStorage.getItem('authToken');
    const [searchParams, setSearchParams] = React.useState({
        reviewId: '',
        childReviewId: '',

    });

    // React.useEffect(() => {
    // console.log("isActive in tabs",isActive);    
    // },[isActive])

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
        if(reviewId === "" && childReviewId === ""){
            showToast("Give Data To Search");
        }else{
            setSearchParams({
                reviewId,
                childReviewId, 
            });
        }
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

  const showToast = (message) => {
    Swal.fire({
      icon: 'error',
      // title: 'Oops...',
      text: message,
      position: 'bottom-left',
      toast: true,
      timer: 5000,
      showConfirmButton: false,
      didClose: () => Swal.close(),
      customClass: {
        popup: 'swal-toast-popup',
      },
      background: 'red',
      color: 'white',
      height: '10%'
    });
  };


    const handleClear = () => {
        setReviewId('');
        setChildReviewId('');
        setSearchParams({
            reviewId: '',
            childReviewId: '',
        });

        setIsRows(true);
    };

    const handleMultiClear = () => {
        setReviewId('');   
        setFromDate('');
        setToDate('');
        setSelectedDivision('');
        setSelectedGroup('');

        console.log("selectedDivision",selectedDivision);
        console.log("selectedGroup",selectedGroup);
        
        

        setSearchMultiParams({
            reviewId: '',
            groupName: '',
            division: '',
            fromDate: '',
            toDate: '',
        })

        setRowData(false);
        
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
        setSelectedGroup(selectedGroup); 

        setSelectedDivision(''); 
        setIsDivisionDisabled(false);

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
                    setDivision(response.data.result);
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

    const handleDivisionChange = (event) => {
        const selectedDivision = event.target.value;
        setSelectedDivision(selectedDivision); 
    };

    const handleMultiSearch = () => {
        if(reviewId === "" && selectedGroup === "" && selectedDivision === ""){
            showToast("Please Select before Search");
            }
        else{            
            setSearchMultiParams({
                reviewId,
                groupName: selectedGroup,
                division: selectedDivision,
                fromDate,
                toDate,
        });

    }
}


    const downloadExcel = async () => {
            console.log("reviewId",reviewId);
            console.log("division",division);
            console.log("groupName",groupName);
            
            
            
        if (reviewId !== "" || division !== "" || groupName !== "") {
            const params = {
                groupName: selectedGroup,
                division: selectedDivision,
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
                        const blob = response.data;
                        const url = window.URL.createObjectURL(blob);

                        const link = document.createElement('a');
                        link.href = url;
                        link.download = filename;
                        link.click(); 


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
            
            showToast("Search data before Download");
        }

    };

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                    <Tab
                        label="GROUP TASK"
                        {...a11yProps(0)}
                        sx={{
                            bgcolor: value === 0 ? 'white' : 'transparent',
                            color: value === 0 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1.5,
                            textTransform: 'none',
                            position: 'relative',
                            '&:hover': {
                                bgcolor: value === 0 ? 'white' : 'white',
                                color: value === 0 ? 'white' : 'black',
                            },
                            '&.Mui-selected': {
                                bgcolor: 'white',
                                color: 'black',
                                borderBottom: '2px solid #FF5E00',
                            },
                        }}
                    />
                    <Tab
                        label="MY TASK"
                        {...a11yProps(0)}
                        sx={{
                            bgcolor: value === 1 ? 'white' : 'transparent',
                            color: value === 1 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1.5,
                            textTransform: 'none',
                            position: 'relative',
                            '&:hover': {
                                bgcolor: value === 1 ? 'white' : '#white',
                                color: value === 1 ? 'white' : 'black',
                            },
                            '&.Mui-selected': {
                                bgcolor: 'white',
                                color: 'black',
                                borderBottom: '2px solid #FF5E00',
                            },
                        }}
                    />
                    <Tab
                        label="SEARCH"
                        {...a11yProps(1)}
                        sx={{
                            bgcolor: value === 2 ? 'white' : 'transparent',
                            color: value === 2 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1,
                            textTransform: 'none',
                            position: 'relative',
                            '&:hover': {
                                bgcolor: value === 2 ? 'white' : 'white',
                                color: value === 2 ? 'white' : 'black',
                            },
                            '&.Mui-selected': {
                                bgcolor: 'white',
                                color: 'black',
                                borderBottom: '2px solid #FF5E00',
                            },
                        }}
                    />

                </Tabs>
            </Box>
            <div className='borderDiv'>
            <ToastContainer />
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
                                value={childReviewId}
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
                        <BasicTable searchParams={searchParams} 
                        buttonClicked={buttonClicked} 
                        setButtonClicked={setButtonClicked} 
                        isRows={isRows}
                        setIsRows={setIsRows}
                        />
                    </div>
                </CustomTabPanel>
                <CustomTabPanel value={value} index={1}>
                    {/* <div className='mainDivs'> */}
                        {/* <div className='ReviewRow'>
                            <label className='ReviewIdLabel'>ReviewID</label>
                            <input
                                className='ReviewIdInput'
                                type='text'
                                value={reviewId}
                                onChange={(e) => setReviewId(e.target.value)} />
                        </div> */}
                        {/* <div>
                            <label className='ReviewIdLabel'>ChildReviewID</label>
                            <input
                                className='ReviewIdInput'
                                type='text'
                                placeholder=''
                                onChange={(e) => setChildReviewId(e.target.value)} />
                        </div> */}
                        {/* <div>
                            <button className='SearchButton' onClick={handleSearch}>
                                Search</button>
                        </div>
                        <div>
                            <button className='SearchButton' onClick={handleClear}>Clear</button>
                        </div> */}
                    {/* </div> */}
                    <div>
                        {/* <BasicTable searchParams={searchParams} buttonClicked={buttonClicked} setButtonClicked={setButtonClicked} /> */}
                        <MyQueueTable />
                    </div>
                </CustomTabPanel>
                <CustomTabPanel value={value} index={2}>
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
                                            borderColor: '#FF5E00',
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
                                            borderColor: '#FF5E00',
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
                                            borderColor: '#FF5E00',
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
                                            borderColor: '#FF5E00',
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
                                            borderColor: '#FF5E00',
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
                            </TextField>
                        </div>                        
                        <div className='SearchButtons'>
                            <button className='CleanButton' onClick={handleMultiSearch}>Search</button></div>
                        <div className='SearchButtons'>
                            <button className='CleanButton' onClick={handleMultiClear}>Clear</button></div>
                    </div>
                    {
                        (rowData === true) ? (
                            <div className='DataExport'>
                            <Button
                                className='dataExportButton'
                                startIcon={<FileDownloadIcon />}
                                sx={{
                                    backgroundColor: '#FF5E00',
                                    textTransform: 'none',
                                    color: 'white',
                                    width : '120px',
                                    height: '40px',
                                    fontSize: 'small',
                                    '&:hover': { backgroundColor: '#FF5E00'
    
                                     }
                                }}
                                onClick={downloadExcel}
                            >
                                Data Export
                            </Button>
                        </div>
                        ) : null
                    }
                    <div>
                        <MultiSearchTable searchMultiParams={searchMultiParams} setRowData={setRowData} />
                    </div>
                </CustomTabPanel>
            </div>

        </Box>
    );
}
