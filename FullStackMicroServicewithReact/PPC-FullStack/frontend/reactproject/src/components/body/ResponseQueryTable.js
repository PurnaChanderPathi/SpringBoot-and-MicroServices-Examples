import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, MenuItem, Modal, Paper, Select, styled, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextareaAutosize, TextField, Tooltip, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react'
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import axios from 'axios';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import { useDispatch, useSelector } from 'react-redux';
import { getResponseQueryFetchDetails } from '../../redux/ResponseQueryFetchDetails';
import { setRowsPerPage, setTotalPages } from '../../redux/ResponseRemedaitionSlice';
import { setEmptyState } from '../../redux/scoreSlice';
import NoteAddIcon from '@mui/icons-material/NoteAdd';
import ReplyIcon from '@mui/icons-material/Reply';
import CloseIcon from '@mui/icons-material/Close';

const ResponseQueryTable = ({ isInserted, setIsInserted }) => {

    //    const [rows, setRows] = React.useState([]);
    //     const [totalPages, setTotalPages] = React.useState(1);
    //     const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);
    const Token = localStorage.getItem('authToken');
    const childReviewId = localStorage.getItem('childReviewId');
    const [isResponseQueryOpen, setIsResponseQueryOpen] = useState(false);
    const [isQuerySequence, setIsQuerySequence] = useState(null);
    const [openToResponse, setOpenToResponse] = useState(false);
    const dispatch = useDispatch();
    const { rows, totalPages, rowsPerPage, error, loading } = useSelector((state) => state.ResponseQuery);
    const role = localStorage.getItem('role');
    const [response, setResponse] = useState('');
    const username = localStorage.getItem('username');
    const date = new Date();
    const timeStamp = date.getTime();

    const handleOpenResponse = () => {
        setOpenToResponse(false);
    }

    const handleCloseResponse = () => {

    }

    const updateResponseQueryDetails = async () => {
        const url = "http://localhost:9195/api/ActionObligor/updateResponseQueryDetails";
        const inputs = {
            response: response,
            responseBy: username,
            querySequence: isQuerySequence,
            responseOn: timeStamp
        }
        console.log("inputs at updateResponseQueryDetails :", inputs);


        try {
            const response = await axios.put(url, inputs, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.data.status === 200) {
                console.log("QueryDetails Updated Successfully...!");
                const result = response.data.result;
                console.log("Updated QueryDetails : ", result);
                dispatch(getResponseQueryFetchDetails(childReviewId, Token));
            }
        } catch (error) {
            console.log("Failed to update QueryDetails : ", error.message);

        }
    }

    const styleResponse = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 800,
        bgcolor: 'background.paper',
        boxShadow: 24,
        // height: 450,
    };

    // useEffect(() => {
    //     console.log("isEmpty in ResponseQueryTable", isEmpty);
    //     if(rows.length > 0){
    //         dispatch(setEmptyState(true));
    //         console.log("isEmpty in ResponseQueryTable :",isEmpty);

    //     }

    // }, [isEmpty,rows,dispatch])



    const handleResponseQueryCancel = () => {
        setIsResponseQueryOpen(false);
    }

    const handleResponseQueryConfirm = () => {
        if (isQuerySequence) {
            handleResponseQueryDelete(isQuerySequence);
        }
        setIsResponseQueryOpen(false);
    }

    // useEffect(() => {
    //     if (isInserted === true) {
    //         getResponseQueryFetchDetails(childReviewId, Token);
    //     }
    //     setIsInserted(false);
    // }, [isInserted])

    const startIndex = (currentPage - 1) * rowsPerPage;
    const displayedRows = Array.isArray(rows)
        ? rows.slice(startIndex, startIndex + rowsPerPage)
        : [];

    // const handleRowsPerPageChange = (event) => {
    //     const value = parseInt(event.target.value, 10);
    //     setRowsPerPage(value);
    //     setTotalPages(Math.ceil(rows.length / value));
    //     setCurrentPage(1);
    // };
    const handleRowsPerPageChange = (event) => {
        const value = Math.max(1, parseInt(event.target.value, 10));
        // setRowsPerPage(value);
        // setTotalPages(Math.ceil(rows.length / value));
        dispatch(setRowsPerPage(value));
        setCurrentPage(1);
    };

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

    // useEffect(() => {
    //     if(childReviewId){
    //         ResponseQueryFetchDetails();
    //     }
    // },[childReviewId])

    useEffect(() => {
        console.log("childReviewId in ResponseQueryTable", childReviewId);
        console.log("Token in ResponseQueryTable", Token);


        if (childReviewId && Token) {
            dispatch(getResponseQueryFetchDetails(childReviewId, Token));
        }
    }, [dispatch, childReviewId, Token])

    useEffect(() => {
        dispatch(setTotalPages(Math.ceil(rows.length / rowsPerPage)));
    }, [rows, rowsPerPage, dispatch]);

    const handleResponseQueryDelete = (querySequence) => {
        ResponseQueryDeleteByQuerySequence(querySequence);
    }

    const ResponseQueryDeleteByQuerySequence = async (querySequence) => {

        let url = `http://localhost:9195/api/ActionObligor/deleteResponseQuery/${querySequence}`;

        try {
            const response = await axios.delete(url, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.data.status === 200) {
                console.log("ResponseQuery Deleted Successfully :", response.data.message);
                // ResponseQueryFetchDetails();  
                dispatch(getResponseQueryFetchDetails(childReviewId, Token));
            }
        } catch (error) {
            console.log("Error in Process Flow", error.message);

        }
    }


    return (
        <Box sx={{ padding: 2, }}>
            <TableContainer component={Paper} sx={{ backgroundColor: "white", border: '1px solid #B2BEB5', height: "15vh" }}>
                <Table sx={{ minWidth: 300 }} aria-label="simple table">
                    <TableHead >
                        <TableRow >
                            <TableCell align="center"
                                sx={{
                                    fontWeight: "bold", border: "1px solid #B2BEB5",
                                    padding: "4px 8px", fontSize: '12px', width: '130px', color: "#0047AB"
                                }}
                            >
                                SL NO.
                                <ArrowUpwardIcon sx={{ alignItems: 'center' }} />
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                QUERY SEQUENCE
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: "12px", color: "#0047AB" }}
                            >
                                QUERY
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                CREATED ON
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                CREATED BY
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                RESPONSE
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                RESPONSE BY
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                RESPONSE ON
                            </TableCell>
                            {
                                (role !== "SPOC") ? (
                                    <TableCell
                                        align="center"
                                        sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                                    >
                                        DELETE
                                    </TableCell>
                                ) : null
                            }
                            {
                                (role === "SPOC") ? (
                                    <TableCell align="center"
                                        sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                                    >Response</TableCell>
                                ) : null
                            }
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId}>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.resQueryId}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.querySequence}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.query}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '11px' }}>
                                    {new Date(row.createdOn).toLocaleString('en-US', {
                                        weekday: 'long',   // "Monday"
                                        year: 'numeric',   // "2024"
                                        month: 'long',     // "November"
                                        day: 'numeric',    // "14"
                                        hour: '2-digit',   // "03"
                                        minute: '2-digit', // "29"
                                        second: '2-digit', // "35"
                                        hour12: true       // "AM/PM"
                                    })}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.createdBy}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.response}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.responseBy}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '11px' }}>
                                    {row.responseOn ? (
                                        new Date(row.responseOn).toLocaleString('en-US', {
                                            weekday: 'long',   // "Monday"
                                            year: 'numeric',   // "2024"
                                            month: 'long',     // "November"
                                            day: 'numeric',    // "14"
                                            hour: '2-digit',   // "03"
                                            minute: '2-digit', // "29"
                                            second: '2-digit', // "35"
                                            hour12: true       // "AM/PM"
                                        })
                                    ) : (
                                        "NaN"
                                    )}
                                </TableCell>
                                {
                                    (role !== "SPOC") ? (
                                        <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                            <Button onClick={() => {
                                                setIsQuerySequence(row.querySequence);
                                                setIsResponseQueryOpen(true);
                                                // handleResponseQueryDelete(row.querySequence);
                                            }}>
                                                <Tooltip>
                                                    <DeleteOutlineIcon sx={{ color: '#FF5E00', }} />
                                                </Tooltip>
                                            </Button>

                                        </TableCell>
                                    ) : null
                                }
                                {
                                    (role === "SPOC") ? (
                                        <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                            <Button onClick={() => {
                                                setOpenToResponse(true);
                                                setIsQuerySequence(row.querySequence);
                                            }}>
                                                <Tooltip title="Response">
                                                    <ReplyIcon sx={{ color: '#FF5E00' }} />
                                                </Tooltip>
                                            </Button>
                                        </TableCell>
                                    ) : null
                                }
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>


            <div className="tablerow" style={{ display: 'flex', justifyContent: 'end', alignItems: 'center' }}>


                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        alignItems: "center",
                        marginBottom: 1,
                        paddingTop: 2
                    }}
                >
                    <Typography variant="body1" sx={{ marginRight: 1, color: '#0047AB', fontWeight: 'bold' }}>
                        selected rows
                    </Typography>
                    <Select
                        value={rowsPerPage}
                        onChange={handleRowsPerPageChange}
                        sx={{
                            width: 60,
                            height: 40,
                            backgroundColor: "transparent",
                            border: 'none',
                            boxShadow: 'none',
                            '& .MuiOutlinedInput-root': {
                                border: 'none',
                                '&:hover': {
                                    border: 'none',
                                },
                                '&.Mui-focused': {
                                    border: 'none',
                                }
                            },
                            '& .MuiSelect-icon': {
                                display: 'none',
                            }
                        }}
                    >
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((count) => (
                            <MenuItem
                                key={count}
                                value={count}
                                sx={{
                                    border: 'none',
                                    padding: '5px 10px',
                                    '&:hover': {
                                        color: '#FF5E00',
                                        border: 'none',
                                    }
                                }}
                            >
                                {count}
                            </MenuItem>
                        ))}
                    </Select>

                </Box>

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        alignItems: "center",
                        marginTop: 2,
                    }}
                >
                    <div style={{ display: 'flex' }}>
                        <Typography variant="body1" sx={{ marginX: 2 }}>
                            {currentPage} - {Math.min(currentPage * rowsPerPage, rows.length)} of{" "}
                            {rows.length}
                        </Typography>

                        <Button
                            variant="contained"
                            onClick={handlePreviousPage}
                            disabled={currentPage === 1}
                            sx={{
                                backgroundColor: "white",
                                color: "#FF5E00",
                                border: 'none',
                                boxShadow: 'none',
                                "&:hover": {
                                    backgroundColor: "white",
                                    border: 'none',
                                    boxShadow: 'none'
                                },
                                marginRight: 1,
                            }}
                            startIcon={<ArrowBackIosNewIcon />}
                        >

                        </Button>

                        <Button
                            variant="contained"
                            onClick={handleNextPage}
                            disabled={currentPage === totalPages}
                            sx={{
                                backgroundColor: 'white',
                                color: "#FF5E00",
                                border: 'none',
                                boxShadow: 'none',
                                "&:hover": {
                                    backgroundColor: "white",
                                    border: 'none',
                                    boxShadow: 'none'
                                },
                                marginLeft: 1,
                            }}
                            startIcon={<ArrowForwardIosIcon sx={{ backgroundColor: 'transparent', }} />}
                        >

                        </Button>
                    </div>
                </Box>
            </div>
            
            <Dialog open={isResponseQueryOpen} sx={{ marginBottom: '190px' }}>
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
                        <Button onClick={handleResponseQueryCancel}><CloseIcon sx={{ color: 'black' }} /></Button>
                    </div>
                    <div className='loader' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh', backgroundColor: 'white' }}>
                        <Typography
                            sx={{ fontWeight: 'bold' }}>
                            <span style={{
                                textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                textUnderlineOffset: '4px'
                            }}
                                className='underlineText'>Do</span> you want to Delete Response Query ?
                        </Typography>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                        <Button onClick={handleResponseQueryConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                            Yes
                        </Button>
                    </div>
                </div>
            </Dialog>
            <Modal
                open={openToResponse}
                onClose={handleCloseResponse}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={styleResponse}>
                    <div className='FieldworkSectionModal'>
                        <div className='FieldWorkHeading' style={{ display: 'flex', alignItems: 'center' }}>
                            <Typography
                                sx={{ fontWeight: 'bold' }}>
                                <span style={{
                                    textDecoration: 'underline',
                                    textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                    textUnderlineOffset: '4px',
                                    borderBottom: "1px solid #B2BEB5"
                                }}
                                    className='underlineText'>ADD</span> RESPONSE
                            </Typography>
                            <Button onClick={handleOpenResponse}><CloseIcon sx={{ color: 'black' }} /></Button>
                        </div>
                        <div className='ResponseQueryScreen' style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center' }}>

                            <div style={{ display: 'flex', flexDirection: 'column', width: '45vw', height: '40vh', justifyContent: 'space-evenly', }}>

                                <div className="ResponseQuery" style={{ width: "100%" }}>
                                    <TextField
                                        label="Response"
                                        value={response}
                                        multiline
                                        minRows={6}
                                        variant="outlined"
                                        fullWidth
                                        onChange={(e) =>
                                            setResponse(e.target.value)
                                        }
                                        style={{
                                            borderRadius: "5px",
                                        }}
                                        InputProps={{
                                            style: {
                                                // padding: "10px",
                                            },
                                        }}
                                        sx={{
                                            "& .MuiOutlinedInput-root": {
                                                "& fieldset": {
                                                    borderColor: "#FF5E00",
                                                },
                                                "&:hover fieldset": {
                                                    borderColor: "#FF5E00",
                                                },
                                                "&.Mui-focused fieldset": {
                                                    borderColor: "#FF5E00",
                                                },
                                            },
                                            "& .MuiInputLabel-root": {
                                                color: "black",
                                            },
                                            "& .MuiInputLabel-root.Mui-focused": {
                                                color: "black",
                                            },
                                        }}
                                    />
                                </div>

                                {/* <CustomTextArea 
                                minRows={10}
                                placeholder='Response' /> */}

                                <Button
                                    startIcon={<NoteAddIcon />} variant='contained' sx={{ backgroundColor: '#FF5E00' }}
                                    onClick={() => {
                                        updateResponseQueryDetails();
                                        setOpenToResponse(false);
                                        setResponse('');
                                    }
                                    }
                                >ADD</Button>
                            </div>

                        </div>

                    </div>
                </Box>
            </Modal>
        </Box>
    )
}

export default ResponseQueryTable
