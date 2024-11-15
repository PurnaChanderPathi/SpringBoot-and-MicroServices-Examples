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

} from '@mui/material';
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';


export default function PlanningStageTable({buttonClicked, setButtonClicked}) {
  const [rows, setRows] = React.useState([]);
  const token = localStorage.getItem('authToken');

  const fetchComments = async () => {
    const reviewId = localStorage.getItem('reviewId');

    try {
      const response = await axios.get(`http://localhost:9195/api/fetch/getAll/${reviewId}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      const availRow=Array.isArray(response?.data?.result)?response?.data?.result:[]

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
  }, [buttonClicked,setButtonClicked]);

  const handleDeleteComment = async (viewComment) => {
    try {
      console.log("viewCommentD",viewComment);
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
          <TableHead sx={{ backgroundColor: 'rgb(37, 74, 158)', color: 'white' }}>
            <TableRow>
              <TableCell sx={{ color: 'white', border: '1px solid black' }}>Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Commented By</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Commented On</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>View Comment</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>Actions</TableCell> {/* Action column for delete */}
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                <TableCell component="th" scope="row" sx={{ border: '1px solid black' }}>
                  {row.reviewId}
                </TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.commentedBy}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{new Date(row.commentedOn).toLocaleString()}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.viewComment}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>
                  <Button
                    onClick={() => handleDeleteComment(row.viewComment)}
                    // variant="contained"
                    // sx={{
                    //   backgroundColor: 'red',
                    //   textTransform: 'none',
                    //   color: 'white',
                    //   '&:hover': { backgroundColor: 'rgba(255, 0, 0, 0.8)' },
                    // }}
                  >
                    {/* Delete */}<DeleteOutlineIcon/>
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
