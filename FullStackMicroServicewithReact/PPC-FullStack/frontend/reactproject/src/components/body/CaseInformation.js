import React, { useEffect, useState } from 'react'
import './CaseInformation.css'
import { useParams } from 'react-router-dom';
import PlanningTabs from './PlanningStage';
import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, MenuItem, TextField } from '@mui/material';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';

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
  const [isFieldDisabled, setIsFieldDisabled] = useState(true);
  const [isCompleted, setIsCompleted] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [documentMesage,setDocumentMessage] = useState('');
  const [documentExist, setDocumentExist] = useState(false);
  

  const showToast = (message) => {
    toast.error(message, {
        position: "bottom-left",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
    });
};

const showToastSuccess = (message) => {
  toast.success(message, {
    position: "top-right",
    autoClose: 5000,
    hideProgressBar: false,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
    // className: "custom-toast",
  })
}


const handleSubmit = () => {
  if(!documentExist){
    setDocumentMessage("Please add a document");
    console.log("documentMesage",documentMesage);
    console.log("Please add a document");
    showToast("Please add a document");
  }else{
    setDocumentMessage('');
    UpdateDetails();
    setTimeout(()=> {
      window.close();
    },5000);
  }
}

const handleDocumentsFetched = (exists) => {
  setDocumentExist(exists);
}


    const UpdateDetails = async () => {
      const role = localStorage.getItem("role");
      console.log("role",role);
      const inputs = {
        reviewId: reviewId,
        action: action,
        role: role,
      }
      console.log("action",action);
      const token = localStorage.getItem('authToken');
      try {
        const response = await axios.put("http://localhost:9195/api/action/submitTask",inputs, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`,
          }
        })
        if(response.status === 200){
          console.log("Data updated Successfully");
          showToastSuccess(response.data.message);
          setAction('');
          setPlanning('');
          localStorage.setItem("role",response.data.result.role);
          console.log("role",response.data.result.role);
          
          
        }else{
          console.log("Failed To Update");
          setAction('');
          setPlanning('');
          
          
        }
      } catch (error) {
        console.log("Error updating Data",error);
        setAction('');
        setPlanning('');    
      }
    }


    // Handle value change
    const handleValueChange = (e) => {
      const value = e.target.value;
      setPlanning(value);
  
      // Disable the field if the value is not empty
      if (value !== "") {
        setIsFieldDisabled(true);
      }
    };

  // Open the modal when "Edit" is clicked
  const handleEditClick = () => {
    setIsModalOpen(true);
  };

  // Handle modal confirmation
  const handleModalConfirm = () => {
    setIsFieldDisabled(false); // Enable the TextField
    setIsModalOpen(false); // Close the modal
  };

  // Handle modal cancellation
  const handleModalCancel = () => {
    setIsModalOpen(false); // Close the modal
  };


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
        localStorage.setItem("role",data.role);
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
      <ToastContainer />
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
              disabled={planning !== 'PlanningCompleted'}
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
              <MenuItem value="SubmittedToHeadofPPC">Submit to Head of PPC</MenuItem>
              {/* <MenuItem value="JPA">JPA</MenuItem>
              <MenuItem value="Servlet">Servlet</MenuItem> */}
            </TextField>
          </div>
          <div className='submitTACI'>
            <Button variant='contained' sx={{ backgroundColor: '#1B4D3E' }} 
            onClick={handleSubmit}
            disabled={action === ''}
            >Submit</Button>
          </div>
        </div>
        <div className='planningEditScreen'>
          <TextField
            label="planning"
            className='GroupNameText'
            id="GroupName"
            select
            value={planning}
            onChange={handleValueChange}
            disabled={isFieldDisabled && planning !== ""}
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
            <MenuItem value="PlanningCompleted">Planning Completed</MenuItem>
            <MenuItem value="Planninginprogress">Planning inprogress</MenuItem>
          </TextField>
          <Button className='BtnEditCI'
            variant='contained'
            sx={{ backgroundColor: '#1B4D3E' }}
            onClick={handleEditClick}
          >
            Edit
          </Button>
          {/* Confirmation Modal */}
          <Dialog open={isModalOpen} onClose={handleModalCancel} sx={{marginBottom: '190px'}}>
            <DialogTitle sx={{color: 'black', fontWeight: 'bold'}}>Confirm Change</DialogTitle>
            <DialogContent>
              <DialogContentText sx={{color: 'black', fontWeight: '600'}}>
                Do you want to change the planning?
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button variant='contained' onClick={handleModalCancel} sx={{backgroundColor: '#1B4D3E'}}>
                No
              </Button>
              <Button variant='contained' onClick={handleModalConfirm} sx={{backgroundColor: '#1B4D3E'}}>
                Yes
              </Button>
            </DialogActions>
          </Dialog>
        </div>
        <div className='SaveAndCloseButtonCI'>
          <Button variant='contained'
            sx={{ backgroundColor: '#1B4D3E' }}>Save & Close</Button>
        </div>
      </div>
      <div className='planningTabsDiv'>
        <PlanningTabs documentMesage={documentMesage} onDocumentsFetched={handleDocumentsFetched}/>
      </div>
    </div>
  )
}

export default CaseInformation
