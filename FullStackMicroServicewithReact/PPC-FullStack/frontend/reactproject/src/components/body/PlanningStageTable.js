import * as React from 'react';
import axios from 'axios';
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Box,
  Button,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
  Typography,

} from '@mui/material';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import CloseIcon from '@mui/icons-material/Close';


export default function PlanningStageTable({ buttonClicked, setButtonClicked }) {
  const [rows, setRows] = React.useState([]);
  const token = localStorage.getItem('authToken');
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [viewComment, setViewComment] = React.useState(null);
  const role = localStorage.getItem('role');

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };


  const handleModalConfirm = () => {
    if (viewComment) {
      handleDeleteComment(viewComment);
      setIsModalOpen(false);
    }
    // if(selectedChildReviewId){
    //     getObligorByChildReviewId(selectedChildReviewId);
    //     setIsModalOpen(false);
    // }
  };

  const fetchComments = async () => {
    const reviewId = localStorage.getItem('reviewId');

    try {
      const response = await axios.get(`http://localhost:9195/api/fetch/getAll/${reviewId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      const availRow = Array.isArray(response?.data?.result) ? response?.data?.result : []

      setRows(availRow);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  React.useEffect(() => {
    fetchComments();
  }, []);

  React.useEffect(() => {
    if (buttonClicked) {
      fetchComments();
      setButtonClicked(false);
    }
  }, [buttonClicked, setButtonClicked]);



  const handleDeleteComment = async (viewComment) => {
    try {
      console.log("viewCommentD", viewComment);
      const response = await axios.delete(`http://localhost:9195/api/comment/delete/${viewComment}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 200) {
        fetchComments();

      }
    } catch (error) {
      console.error('Error deleting comment:', error);
    }
  };

  return (
    <Box sx={{ padding: 2 }}>
      {/* Table */}
      <TableContainer component={Paper}>
        <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
          <TableHead sx={{ backgroundColor: 'transparent', color: 'white' }}>
            <TableRow>
              <TableCell sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Commented By</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Commented On</TableCell>
              <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>View Comment</TableCell>
              {
                (role === "SrCreditReviewer") ? (
                  <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5', fontWeight: 'bold' }}>Actions</TableCell>
                ) : null
              }
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                <TableCell component="th" scope="row" sx={{ color: 'black', border: '1px solid #B2BEB5' }}>
                  {row.reviewId}
                </TableCell>
                <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5' }}>{row.commentedBy}</TableCell>
                <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5' }}>{new Date(row.commentedOn).toLocaleString()}</TableCell>
                <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5' }}>{row.viewComment}</TableCell>

                {
                  (role === "SrCreditReviewer") ? (
                    <TableCell align="right" sx={{ color: 'black', border: '1px solid #B2BEB5' }}>
                      <Button
                        onClick={() => {
                          setViewComment(row.viewComment);
                          setIsModalOpen(true);
                        }
                        }
                      >
                        <Tooltip title="Delete">
                          <DeleteOutlineIcon sx={{ color: '#FF5E00' }} />
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
                    className='underlineText'>Do</span> you want to Delete Comment ?
                </Typography>
              </div>
              <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                <Button onClick={handleModalConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                  Yes
                </Button>
              </div>
            </div>
          </Dialog>
    </Box>
  );
}
