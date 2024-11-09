import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import { Button, Input, MenuItem, TextField } from '@mui/material';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import './Tabs.css'
import BasicTable from './Table';
import MultiSearchTable from './MultiSearchTable';

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

export default function BasicTabs() {
    const [value, setValue] = React.useState(0);
    const [reviewId, setReviewId] = React.useState('');
    const [childReviewId, setChildReviewId] = React.useState('');
    const [groupName, setGroupName] = React.useState('');
    const [division, setDivision] = React.useState('');
    const [fromDate, setFromDate] = React.useState('');
    const [toDate, setToDate] = React.useState('');
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

    const handleMultiSearch = () => {
        setSearchMultiParams({
            reviewId, 
            groupName,
            division,
            fromDate,
            toDate,
        })
    }

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

    return (
        <Box sx={{ width: '100%' }}>
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab
                        label="my queue"
                        {...a11yProps(0)}
                        sx={{
                            bgcolor: value === 0 ? 'rgb(37, 74, 158)' : 'transparent',
                            color: value === 0 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1.5,
                            textTransform: 'none',
                            position: 'relative',
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
                        label="search"
                        {...a11yProps(1)}
                        sx={{
                            bgcolor: value === 1 ? 'rgb(37, 74, 158)' : 'transparent',
                            color: value === 1 ? 'white' : 'black',
                            borderTopLeftRadius: 5,
                            borderTopRightRadius: 5,
                            marginLeft: 1,
                            textTransform: 'none',
                            position: 'relative',
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
                        <BasicTable searchParams={searchParams} />
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
                                value={groupName}
                                onChange={(e) => setGroupName(e.target.value)}
                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                <MenuItem value="JDBC">JDBC</MenuItem>
                                <MenuItem value="JPA">JPA</MenuItem>
                                <MenuItem value="Servlet">Servlet</MenuItem>
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>Division</label>
                            <TextField className='DivisionText'
                                id="Division"
                                select
                                value={division}
                                onChange={(e) => setDivision(e.target.value)}

                            >
                                <MenuItem value="">
                                    <em>None</em>
                                </MenuItem>
                                <MenuItem value="JDBC_Query">JDBC_Query</MenuItem>
                                <MenuItem value="JPA_Query">JPA_Query</MenuItem>
                                <MenuItem value="Servlet_Query">Servlet_Query</MenuItem>
                            </TextField>
                        </div>
                        <div className='GroupName'>
                            <label className='GroupNameLabel'>ReviewId</label>
                            <TextField className='ReviewText'
                                id="ReviewId"
                                value={reviewId}
                                onChange={(e) => setReviewId(e.target.value)}
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
                                backgroundColor: 'rgb(37, 74, 158)', 
                                textTransform: 'none', 
                                color: 'white', 
                                '&:hover': { backgroundColor: 'rgba(37, 74, 158, 0.8)' } }}
                        >
                            Data Export
                        </Button>
                    </div>
                    <div>
                        <MultiSearchTable searchMultiParams={searchMultiParams}/>
                    </div>
                </CustomTabPanel>
            </div>

        </Box>
    );
}