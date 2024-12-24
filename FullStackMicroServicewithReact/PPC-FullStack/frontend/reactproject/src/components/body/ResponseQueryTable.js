import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, MenuItem, Modal, Paper, Select, styled, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextareaAutosize, Tooltip, Typography } from '@mui/material';
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

    const handleOpenResponse = () => {
        setOpenToResponse(false);
    }

    const handleCloseResponse = () => {

    }

        const CustomTextArea = styled(TextareaAutosize)({
            '::placeholder': {
                color : 'Black',
                fontSize : '18px'
            }
        })

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
                getResponseQueryFetchDetails(childReviewId, Token);
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
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                DELETE
                            </TableCell>
                            {
                                (role === "SPOC") ? (
                                    <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Response</TableCell>
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
                                        // second: '2-digit', // "35"
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
                                    {new Date(row.responseOn).toLocaleString('en-US', {
                                        weekday: 'long',   // "Monday"
                                        year: 'numeric',   // "2024"
                                        month: 'long',     // "November"
                                        day: 'numeric',    // "14"
                                        hour: '2-digit',   // "03"
                                        minute: '2-digit', // "29"
                                        // second: '2-digit', // "35"
                                        hour12: true       // "AM/PM"
                                    })}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    <Button onClick={() => {
                                        setIsQuerySequence(row.querySequence);
                                        setIsResponseQueryOpen(true);
                                    }}>
                                        <Tooltip>
                                            <DeleteOutlineIcon sx={{ color: '#FF5E00', }} />
                                        </Tooltip>
                                    </Button>

                                </TableCell>
                                {
                                    (role === "SPOC") ? (
                                        <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                            <Button onClick={() => {
                                                setOpenToResponse(true);
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

            <Dialog open={isResponseQueryOpen} onClose={handleResponseQueryCancel} sx={{ marginBottom: '190px' }}>
                <DialogTitle sx={{ color: 'black', fontWeight: 'bold' }}>Confirm Change</DialogTitle>
                <DialogContent>
                    <DialogContentText sx={{ color: 'black', fontWeight: '600' }}>
                        Do you want to Delete Response Query ?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button variant='contained' onClick={handleResponseQueryCancel} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                        No
                    </Button>
                    <Button variant='contained' onClick={handleResponseQueryConfirm} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                        Yes
                    </Button>
                </DialogActions>
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

                            <div style={{ display: 'flex', flexDirection: 'column', width: '45vw', height: '40vh', justifyContent: 'space-evenly', padding: '20px', gap: '20px' }}>

                                <CustomTextArea minRows={15} placeholder='Response'
                                    sx={{
                                        '&::placeholder': {
                                            padding: '10px',
                                            fontSize: '26px',
                                            color: '#black',
                                            opacity: 1,
                                        },
                                    }}
                                    style={{


                                        border: '1px solid #FF5E00', borderRadius: '5px',
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
                                        }
                                    }}
                                />

                                {/* <CustomTextArea 
                                minRows={10}
                                placeholder='Response' /> */}

                                <Button
                                    startIcon={<NoteAddIcon />} variant='contained' sx={{ backgroundColor: '#FF5E00' }}
                                // onClick={handleObservationObligor}
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
