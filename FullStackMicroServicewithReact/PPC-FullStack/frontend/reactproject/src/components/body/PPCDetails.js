import React, { useEffect } from 'react';
import './PPCDetails.css';
import { MenuItem, TextField } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';

const PPCDetails = ({ handleClose }) => {
  const [reviewId, setReviewId] = React.useState('');
  const [groupName, setGroupName] = React.useState('');
  const [division, setDivision] = React.useState('');


  useEffect(() => {
    if (reviewId) {
      console.log('Updated reviewId:', reviewId); // This will log the updated reviewId value
    }
  }, [reviewId]); // Runs every time reviewId changes

  // Fetch the reviewId when the component mounts  
  useEffect(() => {

    const fetchReviewId = async () => {
      try {
        const response = await fetch('http://localhost:9193/api/query/generateReviewId', {
          method: 'GET', // Use POST method
          headers: {
            'Content-Type': 'application/json', // Set the Content-Type to JSON
          },
        });
        console.log("response object :",response);

        if (response.ok) {
          const data = await response.text(); // Assuming the response is JSON
          console.log('Received data:', data);
          setReviewId(data); // Set the reviewId from the response
          console.log("reviewId :",reviewId);
        } else {
          console.error('Failed to fetch reviewId', response.status);
        }
      } catch (error) {
        console.error('Error fetching reviewId:', error);
      }
    };

    fetchReviewId(); 
    // insertData();// Call the function to fetch the reviewId
  }, [reviewId]); // Dependency array ensures it re-fetches when groupName or division changes

  const inputs = {
    reviewId: reviewId,
    groupName: groupName,
    division: division,
  };
  
  const insertData = async () => {
    try {
      const response = await fetch('http://localhost:9193/api/query/save', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json', // Set the Content-Type to JSON
        },
        body: JSON.stringify(inputs), // Correctly stringify the body
      });
  
      if (response.ok) {
        // Check if the response is OK (status code 2xx)
        console.log('Data inserted successfully');
        setReviewId('');
        setGroupName('');
        setDivision('');

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
    handleClose(); // Close the modal when clicking the exit button
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
              onChange={(e) => setReviewId(e.target.value)} // Optional: to allow manual editing if needed
              disabled // Prevent editing of the ReviewId
              sx={{
                '& .MuiInputBase-root': {
                  fontWeight: 'bold', 

                },
                '& .MuiInputLabel-root': {
                  fontWeight: 'bold', // To make the label bold
                }
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
              onChange={(e) => setGroupName(e.target.value)} // Update state when user selects a group name
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
              onChange={(e) => setDivision(e.target.value)} // Update state when user selects a division
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
