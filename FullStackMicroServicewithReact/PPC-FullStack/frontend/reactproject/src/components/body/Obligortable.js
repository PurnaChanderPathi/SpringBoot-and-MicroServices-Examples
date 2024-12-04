import { Box, Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, Typography } from '@mui/material'
import React from 'react'
import PlayArrowIcon from '@mui/icons-material/PlayArrow';

const Obligortable = () => {

    const [rows, setRows] = React.useState([]);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [totalPages, setTotalPages] = React.useState(1);

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
                            <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Start Case</TableCell>
                            <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Review ID</TableCell>
                            {/* <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Child Review ID</TableCell>
<TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Issue ID</TableCell>
<TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Track Issue ID</TableCell> */}
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Division</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Group Name</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Current Status</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>AssignedTo</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Role</TableCell>
                            <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>CreateBy</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                                <TableCell align='center' sx={{ color: 'white', border: '1px solid #B2BEB5' }}
                                   //* onClick={() => handleStartCaseClick(row.reviewId) **/}
                                ><PlayArrowIcon style={{ color: 'FF5E00' }} /></TableCell>
                                <TableCell component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>
                                    {row.reviewId}
                                </TableCell>
                                {/* <TableCell align="right" sx={{ border: '1px solid black' }}>{row.childReviewId}</TableCell>
<TableCell align="right" sx={{ border: '1px solid black' }}>{row.issueId}</TableCell>
<TableCell align="right" sx={{ border: '1px solid black' }}>{row.trackIssueId}</TableCell> */}
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.division}</TableCell>
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.groupName}</TableCell>
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.currentStatus}</TableCell>
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.assignedTo}</TableCell>
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.role}</TableCell>
                                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.createdBy}</TableCell>
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
