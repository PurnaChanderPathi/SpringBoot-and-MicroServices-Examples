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

} from '@mui/material';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';


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
      <Dialog open={isModalOpen} onClose={handleModalCancel} sx={{ marginBottom: '190px' }}>
        <DialogTitle sx={{ color: 'black', fontWeight: 'bold' }}>Confirm Change</DialogTitle>
        <DialogContent>
          <DialogContentText sx={{ color: 'black', fontWeight: '600' }}>
            Do you want to Delete Comment ?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button variant='contained' onClick={handleModalCancel} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
            No
          </Button>
          <Button variant='contained' onClick={handleModalConfirm} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
            Yes
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
}
