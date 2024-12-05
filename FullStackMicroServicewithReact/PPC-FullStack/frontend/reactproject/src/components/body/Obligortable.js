import { Box, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from '@mui/material'
import React, { useEffect } from 'react'
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import OutboxIcon from '@mui/icons-material/Outbox';
import AddTaskIcon from '@mui/icons-material/AddTask';
import DeleteIcon from '@mui/icons-material/Delete';
import PreviewIcon from '@mui/icons-material/Preview';

const Obligortable = ({ObligorDetails,handleDelete}) => {

    const [rows, setRows] = React.useState([]);
    const [totalPages, setTotalPages] = React.useState(1);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);

    useEffect(() => {
        console.log("Obligor Details at Obligor table ",ObligorDetails);
        
        if(Array.isArray(ObligorDetails) && ObligorDetails.length > 0){
            setRows(ObligorDetails);
            setTotalPages(Math.ceil(ObligorDetails.length / rowsPerPage));
        }else{
            setRows([]);
            setTotalPages(1);
        }
    },[ObligorDetails,rowsPerPage]);


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
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><OutboxIcon sx={{color : '#FF5E00'}} /></TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><AddTaskIcon sx={{color : '#FF5E00'}}/></TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><DeleteIcon sx={{color : '#FF5E00'}} onClick = {() => {handleDelete(row.obligorId)}}/></TableCell>
                                <TableCell align="center" sx={{ border: '1px solid #B2BEB5' }}><PreviewIcon sx={{color : '#FF5E00'}}/></TableCell>
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

export default Obligortable
