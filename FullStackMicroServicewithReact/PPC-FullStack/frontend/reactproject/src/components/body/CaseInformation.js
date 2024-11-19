import React, { useEffect, useState } from 'react'
import './CaseInformation.css'
import { useParams } from 'react-router-dom';
import PlanningTabs from './PlanningStage';
import { Button, MenuItem, TextField } from '@mui/material';

const CaseInformation = () => {
  const { reviewId } = useParams();
  const [caseData, setCaseData] = useState({
    reviewId: '',
    groupName: '',
    division: '',
    role: '',
    assignedToUser: '',
  })
  const [loading, setLoading] = useState(true);
  const [action, setAction] = useState('');
  const [planning, setPlanning] = useState('');
  const ApiToken = localStorage.getItem("authToken");
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const response = await fetch(`http://localhost:9195/api/query/${reviewId}`, {
          method: "GET",
          headers: {
            'Authorization': `Bearer ${ApiToken}`,
            'Content-Type': 'application/json',
          }
        });
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        const data = await response.json();
        setCaseData({
          reviewId: data.reviewId,
          groupName: data.groupName,
          division: data.division,
          role: data.role,
          assignedToUser: data.assignedToUser
        });
        localStorage.setItem("reviewId", data.reviewId);
      } catch (error) {
        console.error('Error fetching data:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [reviewId, ApiToken]);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div className='CaseInfoMainDiv'>
      <div className='CaseInfoScreen'>
        <div className='CaseInfoheading'>
          Case Information
        </div>
      </div>
      <div className='fetchCaseInformation'>
        <div className='ReviewIdinfoCS'>
          <div className='ReviewLabelCS'>
            ReviewId
          </div>
          <div className='ReviewInputCS'>
            <input type='text' value={caseData.reviewId}
              className='inputReviewCS'
              disabled />
          </div>

        </div>
        <div className='ReviewIdinfoCS'>
          <div className='ReviewLabelCS'>
            GroupName
          </div>
          <div className='ReviewInputCS'>
            <input type='text'
              value={caseData.groupName}
              className='inputReviewCS'
              disabled />
          </div>
        </div>
        <div className='ReviewIdinfoCS'>
          <div className='ReviewLabelCS'>
            Division
          </div>
          <div className='ReviewInputCS'>
            <input type='text'
              value={caseData.division}
              className='inputReviewCS'
              disabled />
          </div>
        </div>
        <div className='ReviewIdinfoCS'>
          <div className='ReviewLabelCS'>
            Role
          </div>
          <div className='ReviewInputCS'>
            <input type='text'
              value={caseData.role}
              className='inputReviewCS' disabled />
          </div>
        </div>
        <div className='ReviewIdinfoCS'>
          <div className='ReviewLabelCS'>
            PPC Initiator
          </div>
          <div className='ReviewInputCS'>
            <input type='text'
              value={caseData.assignedToUser}
              className='inputReviewCS' disabled />
          </div>
        </div>
      </div>
      <div className='PlanningMainDivCI'>
        <div className='PlanningStageCmptd'>
          <div className='TakeActionSubmitCS'>
            <TextField
              label="TakeAction"
              className='GroupNameText'
              id="GroupName"
              select
              value={action}
              onChange={(e) => setAction(e.target.value)}
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
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="JDBC">Submit To Head of PPC</MenuItem>
              {/* <MenuItem value="JPA">JPA</MenuItem>
              <MenuItem value="Servlet">Servlet</MenuItem> */}
            </TextField>
          </div>
          <div className='submitTACI'>
            <Button variant='contained' sx={{ backgroundColor: '#1B4D3E' }} >Submit</Button>
          </div>
        </div>
        <div className='planningEditScreen'>
          <TextField
              label="planning"
              className='GroupNameText'
              id="GroupName"
              select
              value={planning}
              onChange={(e) => setPlanning(e.target.value)}
              sx={{
                width: '300px',
                textAlign: 'center',
                paddingLeft: '10px',
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
              <MenuItem value="">
                <em>None</em>
              </MenuItem>
              <MenuItem value="JDBC">Planning Completed</MenuItem>
              <MenuItem value="JPA">Planning inCompleted</MenuItem>
            </TextField>
            <Button className='BtnEditCI' variant='contained' sx={{backgroundColor: '#1B4D3E'}}>Edit</Button>
          </div>
      </div>



      <div className='planningTabsDiv'>
        <PlanningTabs />
      </div>
    </div>
  )
}

export default CaseInformation
