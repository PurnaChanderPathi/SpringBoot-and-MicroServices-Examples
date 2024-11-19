import { Box, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Button } from '@mui/material';
import axios from 'axios';
import React, { useState, useEffect } from 'react';

export default function AuditTrail() {
  const [rows, setRows] = useState([]);
  const token = localStorage.getItem('authToken');

  const fetchAuditData = async () => {

    const reviewId = localStorage.getItem('reviewId');
    console.log("reviewIdAT",reviewId);
    
    if (!reviewId) return;

    const url = `http://localhost:9195/api/QueryAT/GetAudit/${reviewId}`;

    try {
      const response = await axios.get(url, {
        headers: {
          'Authorization': `Bearer ${token}`,
          'Content-Type': 'application/json',
        }
      });

      if (response.status === 200) {
        setRows(response.data.result);
        console.log("response",response.data.result);
      } else {
        console.log("Failed to fetch audit data");
      }
    } catch (error) {
      console.log("Error fetching audit data:", error);
    }
  };

  useEffect(() => {
    fetchAuditData();
  }, []);

  return (
    <Box sx={{ padding: 2 }}>
      <TableContainer component={Paper} sx={{ marginTop: 2 }}>
        <Table sx={{ minWidth: 650, borderCollapse: 'collapse' }} aria-label="simple table">
          <TableHead sx={{ backgroundColor: '#1B4D3E', color: 'white' }}>
            <TableRow>
              <TableCell sx={{ color: 'white', border: '1px solid black' }}>Review ID</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>currentAction</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>inTime</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>outTime</TableCell>
              <TableCell align="right" sx={{ color: 'white', border: '1px solid black' }}>actionedBy</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: 'white' }}>
                <TableCell component="th" scope="row" sx={{ border: '1px solid black' }}>
                  {row.reviewId}
                </TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.currentAction}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>
                  {new Date(row.inTime).toLocaleString('en-US',{
                      weekday: 'long',   // "Monday"
                      year: 'numeric',   // "2024"
                      month: 'long',     // "November"
                      day: 'numeric',    // "14"
                      hour: '2-digit',   // "03"
                      minute: '2-digit', // "29"
                      second: '2-digit', // "35"
                      hour12: true       // "AM/PM"
                  })}
                </TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>
                {new Date(row.outTime).toLocaleString('en-US', {
                      weekday: 'long',   // "Monday"
                      year: 'numeric',   // "2024"
                      month: 'long',     // "November"
                      day: 'numeric',    // "14"
                      hour: '2-digit',   // "03"
                      minute: '2-digit', // "29"
                      second: '2-digit', // "35"
                      hour12: true       // "AM/PM"
                })}</TableCell>
                <TableCell align="right" sx={{ border: '1px solid black' }}>{row.actionedBy}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
}
