import React, { useEffect, useState } from 'react';
import './PPCDetails.css';
import { MenuItem, TextField } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import axios from 'axios';

const PPCDetails = ({ handleClose, showToast }) => {
  const [reviewId, setReviewId] = React.useState('');
  const [groupName, setGroupName] = React.useState('');
  const [division, setDivision] = React.useState('');
  const token = localStorage.getItem('authToken');
  const [buttonClicked,setButtonClicked] = useState(false);

  useEffect(() => {
    if (reviewId) {
      console.log('Updated reviewId:', reviewId);
    }
  }, [reviewId]);
  
  useEffect(() => {

    const fetchReviewId = async () => {
      try {
        const response = await axios.get('http://localhost:9195/api/query/generateReviewId', {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          },
        });
        console.log("response object :", response);

        if (response.status === 200) {
          const data = response.data;
          console.log('Received data:', data);
          setReviewId(data);
          console.log("reviewId :", reviewId);
          setButtonClicked(true); 
        } else {
          console.error('Failed to fetch reviewId', response.status);
        }
      } catch (error) {
        console.error('Error fetching reviewId:', error);
      }
    };

    fetchReviewId();
  }, []);

  const inputs = {
    reviewId: reviewId,
    groupName: groupName,
    division: division,
  };

  const insertData = async () => {
    try {
      const response = await axios.post('http://localhost:9195/api/action/save', inputs, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      if (response.status === 200) {
        console.log('Data inserted successfully');
        setReviewId('');
        setGroupName('');
        setDivision('');
        showToast(response.data.message);
        handleClose();
      } else {
        console.error('Failed to insert data', response.status);
      }
    } catch (error) {
      console.error('Error inserting data', error);
      setReviewId('');
      setGroupName('');
      setDivision('');
    }
  };

  const exitButton = () => {
    handleClose();
  };

  return (
    <div className='PPCDetails'>
      <div className='PPCInitial'>
        <div className='PPCheading'>
          PPC DETAILS
          <button className='ExitIconButton' onClick={exitButton}>
            <CloseIcon className='ExitIcon' />
          </button>
        </div>
        <div className='PPCD'>
          <div className='GroupNamePPC'>
            <label className='GroupNameLabelPPC'>ReviewId</label>
            <TextField
              className='ReviewText'
              id="ReviewId"
              value={reviewId}
              onChange={(e) => setReviewId(e.target.value)}
              disabled
              sx={{
                '& .MuiInputBase-root': {
                  fontWeight: 'bold',
                },
                '& .MuiInputLabel-root': {
                  fontWeight: 'bold',
                },
              }}
            />
          </div>
          <div className='GroupNamePPC'>
            <label className='GroupNameLabelPPC'>Group Name</label>
            <TextField
              className='GroupNameText'
              id="GroupName"
              select
              value={groupName}
              onChange={(e) => setGroupName(e.target.value)}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="JDBC">JDBC</MenuItem>
              <MenuItem value="JPA">JPA</MenuItem>
              <MenuItem value="Servlet">Servlet</MenuItem>
            </TextField>
          </div>
          <div className='GroupNamePPC'>
            <label className='GroupNameLabelPPC'>Division</label>
            <TextField
              className='DivisionText'
              id="Division"
              select
              value={division}
              onChange={(e) => setDivision(e.target.value)}
            >
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="JDBC_Query">JDBC_Query</MenuItem>
              <MenuItem value="JPA_Query">JPA_Query</MenuItem>
              <MenuItem value="Servlet_Query">Servlet_Query</MenuItem>
            </TextField>
          </div>
        </div>
        <div className='PPCButton'>
          <button id='createPPC' onClick={insertData}>Create Case</button>
          <button id='closePPC' onClick={exitButton}>Close</button>
        </div>
      </div>
    </div>
  );
};

export default PPCDetails;
