import * as React from 'react';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  TextField,
  Box,
  Button,
  Typography,
} from '@mui/material';

export default function MultiSearchTable({ searchMultiParams }) {
  console.log("searchMultiParams :", searchMultiParams);
  const [rows, setRows] = React.useState([]);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPages, setTotalPages] = React.useState(1);

  // Fetch data based on searchParams when the searchMultiParams changes
  React.useEffect(() => {
    if (Object.values(searchMultiParams).every(val => !val)) {
        setRows([]);
        setTotalPages(1);
        setCurrentPage(1);
      return; // Do not fetch if search parameters are empty
    }

    const fetchData = async () => {
      let url = 'http://localhost:9193/search?'; // Base URL for search API

      // Build the query string based on the provided searchParams
      const { groupName, division, reviewId, fromDate, toDate } = searchMultiParams;
      const queryParams = [];

      if (groupName) {
        queryParams.push(`groupName=${groupName}`);
      }
      if (division) {
        queryParams.push(`division=${division}`);
      }
      if (reviewId) {
        queryParams.push(`reviewId=${reviewId}`);
      }
      if (fromDate) {
        queryParams.push(`fromDate=${fromDate}`);
      }
      if (toDate) {
        queryParams.push(`toDate=${toDate}`);
      }

      if (queryParams.length > 0) {
        url += queryParams.join('&');
      }

      try {
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setRows(data);
        setTotalPages(Math.ceil(data.length / rowsPerPage)); // Calculate total pages for pagination
      } catch (error) {
        console.error('Fetch error:', error);
      }
    };

    fetchData();
  }, [searchMultiParams, rowsPerPage]); // Trigger when searchMultiParams or rowsPerPage changes

  const handleRowsPerPageChange = (event) => {
    const value = Math.max(1, parseInt(event.target.value, 10));
    setRowsPerPage(value);
    setTotalPages(Math.ceil(rows.length / value));
    setCurrentPage(1); // Reset to the first page when rows per page changes
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

  const startIndex = (currentPage - 1) * rowsPerPage;
  const displayedRows = rows.slice(startIndex, startIndex + rowsPerPage);

  return (
    <Box sx={{ padding: 2 }}>
      <Box
        sx={{
          display: 'flex',
          justifyContent: 'flex-end',
          alignItems: 'center',
          marginBottom: 0,
          backgroundColor: 'rgb(37, 74, 158)',
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
          sx={{ width: '100px', marginRight: 2, backgroundColor: 'white' }}
        />
      </Box>

      {/* Table */}
      <TableContainer component={Paper} sx={{ backgroundColor: 'rgb(37, 74, 158)', marginTop: 0 }}>
        <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
          <TableHead sx={{ backgroundColor: 'rgb(37, 74, 158)', color: 'white' }}>
            <TableRow>
              <TableCell sx={{ color: 'white', border: '1px solid black' }}>Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Child Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Issue ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Track Issue ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Division</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Group Name</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Task Status</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Assigned To User</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Role</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {displayedRows.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                <TableCell component="th" scope="row" sx={{ border: '1px solid black' }}>
                  {row.reviewId}
                </TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.childReviewId}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.issueId}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.trackIssueId}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.division}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.groupName}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.taskStatus}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.assignedToUser}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.role}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* Pagination */}
      <Box sx={{ display: 'flex', justifyContent: 'flex-end', alignItems: 'center', marginTop: 2 }}>
        <Button
          variant="contained"
          onClick={handlePreviousPage}
          disabled={currentPage === 1}
          sx={{ marginRight: 1 }}
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
        >
          Next
        </Button>
      </Box>
    </Box>
  );
}
