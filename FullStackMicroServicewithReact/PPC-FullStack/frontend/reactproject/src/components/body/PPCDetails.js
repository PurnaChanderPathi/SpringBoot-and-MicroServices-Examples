import React, { useEffect, useState } from 'react';
import './PPCDetails.css';
import { CircularProgress, MenuItem, TextField } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import axios from 'axios';

const PPCDetails = ({ handleClose, showToast }) => {
  const [reviewId, setReviewId] = React.useState('');
  const [groupName, setGroupName] = React.useState('');
  const [division, setDivision] = React.useState('');
  const [selectedGroup, setSelectedGroup] = useState('');
  const [selectedDivision, setSelectedDivision] = useState('');
  const [loadingGroupNames, setLoadingGroupNames] = useState(true);
  const [loadingDivisions, setLoadingDivisions] = useState(false);
  const [isDivisionDisabled, setIsDivisionDisabled] = useState(true);
  const token = localStorage.getItem('authToken');
  const [buttonClicked, setButtonClicked] = useState(false);

  useEffect(() => {
    const fetchGroupNames = async () => {
      try {
        const response = await axios.get('http://localhost:9195/api/adminConfig/getGroupNames', {
          headers: {

            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,

          }
        });
        console.log("GroupNames API response:", response.data);
        if (response.data && Array.isArray(response.data.result)) {
          setGroupName(response.data.result);
        } else {
          console.error("Expected array but received:", response.data);
          setGroupName([]);
        }
        setLoadingGroupNames(false);
      } catch (error) {
        console.error("Error fetching group names", error);
        setGroupName([]);
        setLoadingGroupNames(false);
      }
    };

    fetchGroupNames();
  }, []);


  // Fetch Divisions when GroupName is selected
  const handleGroupChange = async (event) => {
    const selectedGroup = event.target.value;
    setSelectedGroup(selectedGroup);
    setSelectedDivision(''); // Reset selected division
    setIsDivisionDisabled(false); // Enable Division dropdown

    if (selectedGroup) {
      setLoadingDivisions(true); // Start loading Divisions
      try {
        const response = await axios.get(`http://localhost:9195/api/adminConfig/getDivisions/${selectedGroup}`, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          }
        }
        );
        console.log("Divisions API response:", response.data);
        if (response.data && Array.isArray(response.data.result)) {
          setDivision(response.data.result)
        } else {
          console.error("Divisions response is not an array", response.data);
          setDivision([]);
        }
      } catch (error) {
        console.error("Error fetching divisions", error);
        setDivision([]); // Reset divisions in case of error
      } finally {
        setLoadingDivisions(false); // Stop loading Divisions
      }
    }
  };

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
    groupName: selectedGroup,
    division: selectedDivision,
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
              value={selectedGroup}
              onChange={handleGroupChange}
              sx={{
                '& .MuiOutlinedInput-root': {
                  '& fieldset': {
                    borderColor: '#1B4D3E',
                  },
                  '&:hover fieldset': {
                    borderColor: '#1B4D3E',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1B4D3E',
                  },
                },
              }}
            >
              {loadingGroupNames ? (
                <MenuItem value="">
                  <CircularProgress size={24} />
                </MenuItem>
              ) : (
                Array.isArray(groupName) && groupName.length > 0 ? (
                  groupName.map((group, index) => (
                    <MenuItem key={index} value={group}>
                      {group}
                    </MenuItem>
                  ))
                ) : (
                  <MenuItem value="">No group names available</MenuItem>
                )
              )}
            </TextField>
          </div>
          <div className='GroupNamePPC'>
            <label className='GroupNameLabelPPC'>Division</label>
            <TextField
              className='DivisionText'
              id="Division"
              select
              value={selectedDivision}
              onChange={(e) => setSelectedDivision(e.target.value)}
              disabled={isDivisionDisabled || loadingDivisions}
              sx={{
                '& .MuiOutlinedInput-root': {
                  '& fieldset': {
                    borderColor: '#1B4D3E',
                  },
                  '&:hover fieldset': {
                    borderColor: '#1B4D3E',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#1B4D3E',
                  },
                },
              }}
            >
              {loadingDivisions ? (
                <MenuItem value="" >
                  <CircularProgress size={34} />
                </MenuItem>
              ) : (
                Array.isArray(division) && division.length > 0 ? (
                  division.map((div,index) => (
                    <MenuItem key={index} value={div}>
                      {div}
                    </MenuItem>
                  ))
                ) : (
                  <MenuItem value="">No divisions available</MenuItem>
                )
              )}
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
