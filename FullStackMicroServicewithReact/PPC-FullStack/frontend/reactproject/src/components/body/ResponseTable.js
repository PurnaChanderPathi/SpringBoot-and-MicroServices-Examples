import { Box, Button, Paper, Radio, RadioGroup, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Tooltip, Typography } from '@mui/material'
import React, { useEffect, useState } from 'react'
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import DeleteIcon from '@mui/icons-material/Delete';
import PreviewIcon from '@mui/icons-material/Preview';
import LibraryAddIcon from '@mui/icons-material/LibraryAdd';
import { useDispatch, useSelector } from 'react-redux';
import { getResponseRemediationDetailsByReviewId, setRowsPerPage, setTotalPages } from '../../redux/ResponseRemedaitionSlice';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import axios from 'axios';

const ResponseTable = (
    // {rows,setTotalPages,totalPages}
) => {

    // const [rows, setRows] = React.useState([]);
    // const [totalPages, setTotalPages] = React.useState(1);
    // const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const dispatch = useDispatch();
    const {rows, totalPages, rowsPerPage, error, loading } = useSelector((state) => state.Response);
    const [currentPage, setCurrentPage] = React.useState(1);
    const reviewId = localStorage.getItem("reviewId");
    const token = localStorage.getItem("authToken");
    const [selectedReviewId, setSelectedReviewId] = useState(null);
    const Token = localStorage.getItem('authToken');

    const handleRadioChange = (childReviewId) => {
        setSelectedReviewId(childReviewId); 
        console.log("selectedReviewId",selectedReviewId);
        
      };

    useEffect(() => {
        console.log("reviewId at ResponseTable",reviewId);
        console.log("token at ResponseTable",token);
        
        
        if (reviewId && token) {
            dispatch(getResponseRemediationDetailsByReviewId(reviewId, token));
        }
    }, [dispatch, reviewId, token]);

    useEffect(() => {
        // if(rows.length){
        //     dispatch(setRowsPerPage(value));
        // }
        dispatch(setTotalPages(Math.ceil(rows.length / rowsPerPage)));

    },[rows, rowsPerPage, dispatch]);

    const deleteResponseByChildReviewId = async (childReviewId) => {

        let url = `http://localhost:9195/api/ActionObligor/deleteResponse/${childReviewId}`;

        console.log("token at deleteResponseByChildReviewId",Token);
        
        try {
            const response = await axios.delete(url, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json',
                }
            });
            if(response.data.status === 200){
                console.log("Response Details Deleted with ChildReviewId",childReviewId);                
            }else{
                console.log("Failed to Delete Response Details with ChildReviewId",childReviewId);
                
            }
        } catch (error) {
            console.log("Failed to Process ");
            
        }
    }
    

    const handleRowsPerPageChange = (event) => {
        const value = Math.max(1, parseInt(event.target.value, 10));
        // setRowsPerPage(value);
        // setTotalPages(Math.ceil(rows.length / value));
        dispatch(setRowsPerPage(value));
        setCurrentPage(1);
      };

      const startIndex = (currentPage - 1) * rowsPerPage;
    //   const displayedRows = Array.isArray(rows) ? rows.slice(startIndex, startIndex + rowsPerPage) : [];
    const displayedRows = rows.slice(startIndex, startIndex + rowsPerPage);

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
                        <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>SELECT</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }} >  CHILD REVIEW ID  <ArrowUpwardIcon /> </TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>OBLIGOR NAME</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>PREM ID</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>CIF ID</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>DIVISION</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>REVIEW STATUS</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>CREATED BY</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>ADD/VIEW QUERY</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>DELETE</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>VIEW/UPLOAD</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                                <TableCell align='center' component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Radio
                                    sx={{color : '#FF5E00',
                                        '&.Mui-checked' : {
                                            color: '#FF5E00',
                                        }
                                    }}
                                    checked={selectedReviewId === row.childReviewId}
                                    onChange={() => handleRadioChange(row.childReviewId)}
                                    />
                                </TableCell>
                                <TableCell align='center' component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>
                                    {row.childReviewId}
                                </TableCell>                                
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorName}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorPremId}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.obligorCifId}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.division}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.reviewStatus}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>{row.createdBy}</TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Button>
                                        <Tooltip title="Add/View Query">
                                        <LibraryAddIcon sx={{ color: '#FF5E00' }} />
                                        </Tooltip>
                                    </Button>                                    
                                    </TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Button>
                                        <Tooltip title="Delete">
                                        <DeleteOutlineIcon sx={{ color: '#FF5E00' }} />
                                        </Tooltip>
                                    </Button>                                    
                                    </TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}>
                                    <Button >
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
        </Box>
    )
}

export default ResponseTable
