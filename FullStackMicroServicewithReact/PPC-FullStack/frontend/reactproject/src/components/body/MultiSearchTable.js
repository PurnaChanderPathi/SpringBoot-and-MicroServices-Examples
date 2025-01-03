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
import Swal from 'sweetalert2';
import axios from 'axios';

export default function MultiSearchTable({ searchMultiParams,setRowData }) {
  console.log("searchMultiParams :", searchMultiParams);
  const [rows, setRows] = React.useState([]);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPages, setTotalPages] = React.useState(1);
  const ApiToken = localStorage.getItem("authToken");


  React.useEffect(() => {
    if (Object.values(searchMultiParams).every(val => !val)) {
        setRows([]);
        setTotalPages(1);
        setCurrentPage(1);
      return; 
    }

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

    

    const fetchData = async () => {
      let url = 'http://localhost:9195/api/query/search?'; 

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
        const response = await axios.get(url, {
            headers : {
                'Authorization': `Bearer ${ApiToken}`,
                'Content-Type': 'application/json',
            }

        });
        if(response.data.status === 200){
          const data = response.data.result || [];
          console.log("Multi Search Response :",data);   
          if(data.length === 0){
            setRows([]);
            setTotalPages(0);  
            showToast("Empty Data");
          }else{
            setRows(data);
            setTotalPages(Math.ceil(data.length / rowsPerPage));
          }  
          if(data.length > 0){
            setRowData(true);
          } else{
            setRowData(false);
          }
        }
        else if (response.data.status === 404){
          setRows('');
          setTotalPages(0);  
          showToast("No Data Found");
        }

      } catch (error) {
        console.error('Fetch error:', error);
        
      }
    };

    fetchData();
  }, [searchMultiParams, rowsPerPage,ApiToken]);

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
  // Safely check that displayedRows is an array before calling .map()
const rowsToDisplay = Array.isArray(displayedRows) ? displayedRows : [];


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
        <Typography variant="body1" sx={{ color: 'black', marginRight: 1 }}>
          Per page:
        </Typography>
        <TextField
          type="number"
          value={rowsPerPage}
          onChange={handleRowsPerPageChange}
          variant="outlined"
          size="small"
          sx={{ width: '100px', marginRight: 2, backgroundColor: 'white',
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

      {/* Table */}
      <TableContainer component={Paper} sx={{ backgroundColor: 'transparent', marginTop: 0 }}>
        <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
          <TableHead sx={{ backgroundColor: 'transparent', color: 'white' }}>
            <TableRow>
              <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>Division</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>Group Name</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>CreatedBy</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>createdDate</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>role</TableCell>
              <TableCell align="right" sx={{color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>AssignedTo</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold'  }}>CurrentStatus</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rowsToDisplay.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                <TableCell component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>
                  {row.reviewId}
                </TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.division}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.groupName}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.createdBy}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.createdDate}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.role}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.assignedTo}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid #B2BEB5' }}>{row.currentStatus}</TableCell>
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
          sx={{ marginRight: 1,  backgroundColor: '#FF5E00', 
            textTransform: 'none', 
            color: 'white', 
            '&:hover': { backgroundColor: '#FF5E00' } }}
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
          sx={{ marginRight: 1,  backgroundColor: '#FF5E00', 
            textTransform: 'none', 
            color: 'white', 
            '&:hover': { backgroundColor: '#FF5E00' } }}
        >
          Next
        </Button>
      </Box>
    </Box>
  );
}
