import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Tooltip, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import OutboxIcon from '@mui/icons-material/Outbox';
import AddTaskIcon from '@mui/icons-material/AddTask';
import DeleteIcon from '@mui/icons-material/Delete';
import PreviewIcon from '@mui/icons-material/Preview';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { getResponseRemediationDetailsByReviewId } from '../../redux/ResponseRemedaitionSlice';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import CloseIcon from '@mui/icons-material/Close';


const Obligortable = ({ ObligorDetails, handleDelete, handleOpen, getObligorDetailsWithChildReviewId, handleOpenObservation }) => {

    const [rows, setRows] = React.useState([]);
    const [totalPages, setTotalPages] = React.useState(1);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);
    const Token = localStorage.getItem('authToken');
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [selectedChildReviewId, setSelectedChildReviewId] = useState(null);
    const [isObligorOpen, setIsObligorOpen] = useState(false);
    const [childReviewId, setChildReviewId] = useState(null);
    const isViewAndUpload = useSelector((state) => state.Score.isViewAndUpload);
    const isChildReviewId = useSelector((state) => state.Score.isChildReviewId);

    const handleObligorCancel = () => {
        setIsObligorOpen(false);
    }

    const handleObligorConfirm = () => {
        if (childReviewId) {
            handleDelete(childReviewId);
        }
        setIsObligorOpen(false);
    }

    const handleModalCancel = () => {
        setIsModalOpen(false);
    };

    const handleModalConfirm = () => {
        if (selectedChildReviewId) {
            getObligorByChildReviewId(selectedChildReviewId);
            setIsModalOpen(false);
        }
    };

    useEffect(() => {
        console.log("Obligor Details at Obligor table ", ObligorDetails);

        if (Array.isArray(ObligorDetails) && ObligorDetails.length > 0) {
            setRows(ObligorDetails);
            setTotalPages(Math.ceil(ObligorDetails.length / rowsPerPage));
        } else {
            setRows([]);
            setTotalPages(1);
        }
    }, [ObligorDetails, rowsPerPage]);


    const handleRowsPerPageChange = (event) => {
        const value = Math.max(1, parseInt(event.target.value, 10));
        setRowsPerPage(value);
        setTotalPages(Math.ceil(rows.length / value));
        setCurrentPage(1);
    };

    const startIndex = (currentPage - 1) * rowsPerPage;
    const displayedRows = Array.isArray(rows) ? rows.slice(startIndex, startIndex + rowsPerPage) : [];

    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage((prev) => prev + 1);
        }
    };

    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage((prev) => prev - 1);
        }
    };


    useEffect(() => {
        if (isViewAndUpload && isChildReviewId) {
            handleGetAndUpdateObligor(isChildReviewId);
        }
    }, [isChildReviewId])

    const handleGetAndUpdateObligor = (childReviewId) => {
        console.log("childReviewId in ObligorTable Page :", childReviewId);
        localStorage.setItem("childReviewId", childReviewId);
        getObligorDetailsWithChildReviewId(childReviewId);
        handleOpen();
    }

    const dispatch = useDispatch();

    const getObligorByChildReviewId = async (childReviewId) => {
        console.log("childReviewId:", childReviewId);
        console.log("Token:", Token);

        if (!Token) {
            console.log("Token is missing. Please provide a valid token.");
            return;
        }

        const url = `http://localhost:9195/api/ActionObligor/saveResponseRemediation`;

        try {
            const response = await axios.post(url, { childReviewId }, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json',
                }
            });

            if (response.data.status === 200) {
                console.log("Obligor details fetched by childReviewId:", response.data.message);
                const reviewId = localStorage.getItem("reviewId");
                const token = localStorage.getItem("authToken");
                dispatch(getResponseRemediationDetailsByReviewId(reviewId, token));
            } else {
                console.log("Failed to fetch Obligor details with childReviewId:", response.data.message);
            }
        } catch (error) {
            console.log("Failed to fetch Obligor Details:", error.message);
        }
    };


    return (
        <Box sx={{ padding: 2 }}>

            <Box
                sx={{
                    display: 'flex',
                    justifyContent: 'flex-end',
                    alignItems: 'center',
                    marginBottom: 0,
                    backgroundColor: 'white',
                    padding: 1,
                }}
            >
                <Typography variant="body1" sx={{ color: 'white', marginRight: 1 }}>
                    Per page:
                </Typography>
                <TextField
                    type="number"
                    value={rowsPerPage}
                    onChange={handleRowsPerPageChange}
                    variant="outlined"
                    size="small"
                    sx={{
                        width: '100px', marginRight: 2, backgroundColor: 'white',
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
                />
            </Box>
            <TableContainer component={Paper} sx={{ backgroundColor: 'white', black: 0 }}>
                <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
                    <TableHead sx={{ backgroundColor: 'white', color: 'white' }}>
                        <TableRow>
                            <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }} >  CHILD REVIEW ID  <ArrowUpwardIcon /> </TableCell>
                            <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>DIVISION</TableCell>
                            {/* <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Child Review ID</TableCell>
<TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Issue ID</TableCell>
<TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Track Issue ID</TableCell> */}
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>OBLIGOR NAME</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>PREM ID</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>CIF ID</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>SEND FOR CLASIFICATION</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>ADD/VIEW OBSERVATION</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>DELETE</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>VIEW/UPLOAD</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                                <TableCell align='center' component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>
                                    {row.childReviewId}
                                </TableCell>
                                {/* <TableCell align="right" sx={{ border: '1px solid black' }}>{row.childReviewId}</TableCell>
<TableCell align="right" sx={{ border: '1px solid black' }}>{row.issueId}</TableCell>
<TableCell align="right" sx={{ border: '1px solid black' }}>{row.trackIssueId}</TableCell> */}
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.division}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorName}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorPremId}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorCifId}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Button onClick={() => {
                                        setSelectedChildReviewId(row.childReviewId);
                                        setIsModalOpen(true);
                                    }}>
                                        <Tooltip title="Send For Clasification">
                                            <OutboxIcon sx={{ color: '#FF5E00' }} />
                                        </Tooltip>
                                    </Button></TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><Button onClick={() => handleOpenObservation()}>
                                    <Tooltip title="Add/View Observation">
                                        <AddTaskIcon sx={{ color: '#FF5E00' }} />
                                    </Tooltip>
                                </Button></TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Button onClick={() => {
                                        setChildReviewId(row.childReviewId);
                                        setIsObligorOpen(true);
                                        // handleDelete(row.obligorId)
                                    }}>
                                        <Tooltip title="Delete">
                                            <DeleteOutlineIcon sx={{ color: '#FF5E00' }} />
                                        </Tooltip>
                                    </Button>
                                </TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><Button
                                    onClick={() => handleGetAndUpdateObligor(row.childReviewId)}>
                                    <Tooltip title="View/Upload">
                                        <PreviewIcon sx={{ color: '#FF5E00' }} />
                                    </Tooltip>
                                </Button></TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Box sx={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginTop: 2 }}>
                <Button
                    variant="contained"
                    onClick={handlePreviousPage}
                    disabled={currentPage === 1}
                    sx={{
                        marginRight: 1, backgroundColor: '#FF5E00',
                        textTransform: 'none',
                        color: 'white',
                        '&:hover': { backgroundColor: '#FF5E00' }
                    }}
                >
                    Previous
                </Button>
                <Typography variant="body1" sx={{ marginX: 2 }}>
                    Page {currentPage} of {totalPages}
                </Typography>
                <Button
                    variant="contained"
                    onClick={handleNextPage}
                    disabled={currentPage === totalPages}
                    sx={{
                        marginRight: 1, backgroundColor: '#FF5E00',
                        textTransform: 'none',
                        color: 'white',
                        '&:hover': { backgroundColor: '#FF5E00' }
                    }}
                >
                    Next
                </Button>
            </Box>
            <Dialog open={isModalOpen} sx={{ marginBottom: '190px' }}>
                <div className='loadingScreen' style={{
                    width: '500px', height: '240px',
                    display: 'flex', flexDirection: 'column', border: '1px solid #B2BEB5'
                }}>
                    <div className='loadingHeader' style={{
                        height: '20vh', display: 'flex',
                        justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #B2BEB5', backgroundColor: 'whitesmoke', paddingLeft: "15px"
                    }}>
                        <Typography
                            sx={{ fontWeight: 'bold' }}>
                            <span style={{
                                textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                textUnderlineOffset: '4px'
                            }}
                                className='underlineText'>Con</span>firm Change
                        </Typography>
                        <Button onClick={handleModalCancel}><CloseIcon sx={{ color: 'black' }} /></Button>
                    </div>
                    <div className='loader' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh', backgroundColor: 'white' }}>
                        <Typography
                            sx={{ fontWeight: 'bold' }}>
                            <span style={{
                                textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                textUnderlineOffset: '4px'
                            }}
                                className='underlineText'>Do</span> you want to Send for Clasification ?
                        </Typography>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                        <Button onClick={handleModalConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                            Yes
                        </Button>
                    </div>
                </div>
            </Dialog>

            <Dialog open={isObligorOpen} sx={{ marginBottom: '190px' }}>
                <div className='loadingScreen' style={{
                    width: '500px', height: '240px',
                    display: 'flex', flexDirection: 'column', border: '1px solid #B2BEB5'
                }}>
                    <div className='loadingHeader' style={{
                        height: '20vh', display: 'flex',
                        justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #B2BEB5', backgroundColor: 'whitesmoke', paddingLeft: "15px"
                    }}>
                        <Typography
                            sx={{ fontWeight: 'bold' }}>
                            <span style={{
                                textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                textUnderlineOffset: '4px'
                            }}
                                className='underlineText'>Con</span>firm Change
                        </Typography>
                        <Button onClick={handleObligorCancel}><CloseIcon sx={{ color: 'black' }} /></Button>
                    </div>
                    <div className='loader' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh', backgroundColor: 'white' }}>
                        <Typography
                            sx={{ fontWeight: 'bold' }}>
                            <span style={{
                                textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                textUnderlineOffset: '4px'
                            }}
                                className='underlineText'>Do</span> you want to Delete obligor ?
                        </Typography>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                        <Button onClick={handleObligorConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                            Yes
                        </Button>
                    </div>
                </div>
            </Dialog>

        </Box>
    )
}

export default Obligortable
