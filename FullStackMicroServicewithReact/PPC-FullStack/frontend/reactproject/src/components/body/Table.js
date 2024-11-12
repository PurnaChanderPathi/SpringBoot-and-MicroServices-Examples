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
import PlayArrowIcon from '@mui/icons-material/PlayArrow';
import { useNavigate } from 'react-router-dom';

export default function BasicTable({ searchParams }) {
  const [rows, setRows] = React.useState([]);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPages, setTotalPages] = React.useState(1);
  const navigate = useNavigate();
  const ApiToken = localStorage.getItem("authToken");

  React.useEffect(() => {

    const fetchData = async () => {
      let url = 'http://localhost:9195/api/query/getAll';

      const { reviewId, childReviewId } = searchParams;
       url = 'http://localhost:9195/api/query/query-details';
      const queryParams = [];
      if (reviewId) {
        queryParams.push(`reviewId=${reviewId}`);
      }
      if (childReviewId) {
        queryParams.push(`childReviewId=${childReviewId}`);
      }
      if (queryParams.length > 0) {
        url = `${url}?${queryParams.join('&')}`;
      }

      try {
        const response = await fetch(url, {
            method : "GET",
            headers : {
                'Authorization': `Bearer ${ApiToken}`, // Pass token here
                'Content-Type': 'application/json',
            }
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setRows(data);
        setTotalPages(Math.ceil(data.length / rowsPerPage));
      } catch (error) {
        console.error('Fetch error:', error);
      }
    };

    fetchData();
  }, [searchParams, rowsPerPage, ApiToken]);

  const handleRowsPerPageChange = (event) => {
    const value = Math.max(1, parseInt(event.target.value, 10));
    setRowsPerPage(value);
    setTotalPages(Math.ceil(rows.length / value));
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

  const startIndex = (currentPage - 1) * rowsPerPage;
  const displayedRows = rows.slice(startIndex, startIndex + rowsPerPage);

  const handleStartCaseClick = (reviewId) => {
    navigate(`/CaseInformation/${reviewId}`);
  }

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
      <TableContainer component={Paper} sx={{ backgroundColor: 'rgb(37, 74, 158)', marginTop: 0 }}>
        <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
          <TableHead sx={{ backgroundColor: 'rgb(37, 74, 158)', color: 'white' }}>
            <TableRow>
            <TableCell sx={{ color: 'white', border: '1px solid black' }}>Start Case</TableCell>
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
            <TableCell sx={{ color: 'white', border: '1px solid black' }}
            onClick={() => handleStartCaseClick(row.reviewId)}
            ><PlayArrowIcon style={{backgroundColor: 'rgb(37, 74, 158)', borderRadius: '5px'}}/></TableCell>
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
