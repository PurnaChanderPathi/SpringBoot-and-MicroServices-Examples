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
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import CircularIndeterminate from '../loginScreen/loadingScreen';
import Swal from 'sweetalert2';



export default function BasicTable({ searchParams, buttonClicked, setButtonClicked,isRows,setIsRows }) {

  console.log("searchParams12", searchParams);

  const [rows, setRows] = React.useState([]);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [totalPages, setTotalPages] = React.useState(1);
  const ApiToken = localStorage.getItem("authToken");
  const [isActive, setIsActive] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  React.useEffect(() => {
    if(isRows === true){
      setRows([]);
      setTotalPages(0);
    }
    setIsRows(false);
  })


  React.useEffect(() => {
    console.log("Component mounted or storage event triggered");


    const checkIsActiveFromLocalStorage = () => {
      const storedIsActive = localStorage.getItem('isActive');
      if (storedIsActive === 'true') {
        setIsActive(true);
        fetchTableData();

        localStorage.setItem('isActive', 'false');
        setIsActive(false);
      }
    };

    checkIsActiveFromLocalStorage();

    const handleStorageChange = (event) => {
      if (event.key === 'isActive' && event.newValue === 'true') {
        setIsActive(true);
        fetchTableData();
        localStorage.setItem('isActive', 'false');
        setIsActive(false);
        console.log("handleStorageChange");

      }
    };

    window.addEventListener('storage', handleStorageChange);
    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  React.useEffect(() => {
    if (buttonClicked) {
      fetchTableData();
    }
  }, [buttonClicked, setButtonClicked]);

  React.useEffect(() => {
    fetchTableData();
  }, [])

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

    const fetchTableData = async () => {
    let role = '';
    let createdBy = '';
    let url = "";
    const rolesArray = JSON.parse(localStorage.getItem("userRoles"));
    const rolesString = rolesArray.join(",");
    console.log(rolesString);

    role = rolesString;
    createdBy = localStorage.getItem('username');

    console.log("role", role);
    console.log("createdBy", createdBy);
       url = `http://localhost:9195/api/query/multiTableSearchGroupTask?role=${role}`;

      try {
        const response = await axios.get(url,{
          headers : {
            'Authorization' : `Bearer ${ApiToken}`,
            'Content-Type': 'application/json'
          }
        });

        if(response.data.status === 200){
          const data = response.data.result || [];
          console.log("MultiTableSearch Result",data);
          const reversedData = data.reverse();
          setRows(reversedData);
      setTotalPages(Math.ceil(data.length / rowsPerPage));
          
        }else if(response.data.status === 404){
          console.log("MultiGroupTask Fetched Empty Data");          
          setRows([]);
          setTotalPages(0);
        }
      } catch (error) {
        console.error('Axios fetch error:', error);

            if (error.response && error.response.status === 404) {
              console.error('Data not found due to 404 error');
              setRows([]);
              setTotalPages(0);
              console.log("Rows after clearing:", rows);
            } else {
              console.error('Other Axios error:', error);
            }
      }
    }

  // const fetchTableData = async () => {
  //   let role = '';
  //   let createdBy = '';
  //   let url = "";
  //   const rolesArray = JSON.parse(localStorage.getItem("userRoles"));
  //   const rolesString = rolesArray.join(",");
  //   console.log(rolesString);

  //   role = rolesString;
  //   createdBy = localStorage.getItem('username');

  //   console.log("role", role);
  //   console.log("createdBy", createdBy);

  //   url = 'http://localhost:9195/api/query/getByRoleAndCreatedBy';
  //   const queryParams = [];
  //   if (role) {
  //     queryParams.push(`role=${role}`);
  //   }
  //   if (queryParams.length > 0) {
  //     url = `${url}?${queryParams.join('&')}&assignedTo=`;
  //     console.log("url", url);
  //   } else {
  //     console.error('Insufficient parameters for RoleAndCreateBy');
  //   }

  //   try {
  //     const response = await axios.get(url, {
  //       headers: {
  //         'Authorization': `Bearer ${ApiToken}`,
  //         'Content-Type': 'application/json',
  //       }
  //     });

  //     const data = response.data.result || [];
  //     console.log("fetchDataTable : ", response.data.result);

  //     const reversedData = data.reverse();


  //     setRows(reversedData);
  //     setTotalPages(Math.ceil(data.length / rowsPerPage));

  //   } catch (error) {
  //     console.error('Axios fetch error:', error);

  //     if (error.response && error.response.status === 404) {
  //       console.error('Data not found due to 404 error');
  //       setRows([]);
  //       setTotalPages(0);
  //       console.log("Rows after clearing:", rows);
  //     } else {
  //       console.error('Other Axios error:', error);
  //     }
  //   }

  // }

// const fetchData = async () => {
//   let url = '';

//   const { reviewId } = searchParams;
//   // const {childReviewId } = searchParams;
//   console.log("reviewId", reviewId);


//   if (reviewId !== "") {
//     url = 'http://localhost:9195/api/query/query-details';
//     const queryParams = [];

//     if (reviewId) {
//       queryParams.push(`reviewId=${reviewId}`);
//     }
//     // if (childReviewId) {
//     //   queryParams.push(`childReviewId=${childReviewId}`);
//     // }

//     if (queryParams.length > 0) {
//       url = `${url}?${queryParams.join('&')}`;
//     }
//     try {
//       const response = await axios.get(url, {
//         headers: {
//           'Authorization': `Bearer ${ApiToken}`,
//           'Content-Type': 'application/json',
//         }
//       });
//       if(response.data.status === 200){
//         const data = response.data.result || [];
//         console.log("Search Response:", data);
//         if (data.length === 0) {
//           setRows([]);
//           setTotalPages(0);  
//           showToast("Empty Data");
//         } else {
//           setRows(data);
//           setTotalPages(Math.ceil(data.length / rowsPerPage));
//         }
//       }
//       else if(response.data.status === 404){
//         setRows([]);
//         setTotalPages(0);
//         showToast("No data found");
//       }
//     } catch (error) {
//       console.error('Axios fetch error:', error);
//       showToast("An error occurred while fetching data");
//     }
//   } else {
//     console.log("ReviewId and childReviewId Both are empty");

//   }
// };


  const fetchData = async () => {
    let url = '';

    const { reviewId } = searchParams;
    const {childReviewId } = searchParams;
    console.log("reviewId", reviewId);


    if (reviewId !== "" || childReviewId !== "") {
      url = 'http://localhost:9195/api/query/multiSearchTable';
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
        const response = await axios.get(url, {
          headers: {
            'Authorization': `Bearer ${ApiToken}`,
            'Content-Type': 'application/json',
          }
        });
        if(response.data.status === 200){
          const data = response.data.result ? [response.data.result] : [];
          console.log("Search Response:", data);
          if (data.length === 0) {
            setRows([]);
            setTotalPages(0);  
            showToast("Empty Data");
          } else {
            setRows(data);
            setTotalPages(Math.ceil(data.length / rowsPerPage));
          }
        }
        else if(response.data.status === 404){
          setRows([]);
          setTotalPages(0);
          showToast("No data found");
        }
      } catch (error) {
        console.error('Axios fetch error:', error);
        showToast("An error occurred while fetching data");
      }
    } else {
      console.log("ReviewId and childReviewId Both are empty");

    }
  };



  React.useEffect(() => {
    fetchData();
  }, [searchParams]);

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
  const displayedRows = Array.isArray(rows) ? rows.slice(startIndex, startIndex + rowsPerPage) : [];

  const userLoad = async () => {
    let assignedTo = "";
    assignedTo = localStorage.getItem('username');
    let reviewId = localStorage.getItem('reviewId');

    console.log("reviewId", reviewId);
    console.log("assignedTo", assignedTo);


    let inputs = {
      reviewId: reviewId,
      assignedTo: assignedTo
    };

    try {
      const response = await axios.put("http://localhost:9195/api/action/update", inputs, {
        headers: {
          'Authorization': `Bearer ${ApiToken}`,
          'Content-Type': 'application/json',
        }
      })
      if (response.data.status === 200) {
        console.log("updated Data", response.data.message);
      } else {
        console.log("Failed to Update Details");
      }
    } catch (error) {
      console.log("Error while processing to update Details", error.message);

    }
  }

  
  const handleStartCaseChildReview = (childReviewId) => {
    setLoading(true);
    const url = `/CaseInformation/${childReviewId}`;
    console.log("childReviewId hitted");
    
    setTimeout(() => {
      setLoading(false);
      window.open(url, '_blank');
    }, 1000);
  }


  const handleStartCaseClick = (reviewId) => {
    setLoading(true);
    console.log("reviewId in table :",reviewId);
    localStorage.setItem('reviewType','reviewId');
    
    
    localStorage.setItem('reviewId', reviewId);
    localStorage.setItem('reviewType','reviewId');
    userLoad();


    const url = `/CaseInformation/${reviewId}`;

    setTimeout(() => {
      setLoading(false);
      fetchTableData();
      window.open(url, '_blank');
    }, 1000);

  }

  return (
    <>
      {loading ? (
        <CircularIndeterminate />
      ) : (
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
                  <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Child Review Id</TableCell>
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
                      // onClick={() => handleStartCaseClick(row.reviewId)}  
                      onClick={() => {
                        const childReviewId = row.childReviewId;
                        console.log("childReviewid in MYQUEUE",childReviewId);
                        localStorage.setItem('reviewId',row.reviewId);
                        
                        
                        if(childReviewId === ""){
                          handleStartCaseClick(row.reviewId);
                          localStorage.setItem("reviewType","reviewId");
                        }else{
                          
                          handleStartCaseChildReview(row.childReviewId);
                          localStorage.setItem("reviewType","childReviewId");
                          localStorage.setItem('childReviewId',row.childReviewId);
                        }
                        
                      } }
                    ><PlayArrowIcon style={{ color: 'FF5E00' }} /></TableCell>
                    <TableCell component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>{row.reviewId}</TableCell>  
                    <TableCell component="th" scope="row" sx={{ border: '1px solid #B2BEB5' }}>{row.childReviewId}</TableCell>
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

    </>
  )
}