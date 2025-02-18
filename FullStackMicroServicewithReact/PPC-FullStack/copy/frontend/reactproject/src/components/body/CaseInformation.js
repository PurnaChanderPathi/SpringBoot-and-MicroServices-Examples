import React, { useEffect, useState } from 'react'
import './CaseInformation.css'
import { useParams } from 'react-router-dom';
import PlanningTabs from './PlanningStage';
import { Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, MenuItem, Modal, TextField } from '@mui/material';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { useDispatch, useSelector } from 'react-redux';
import AssignmentStage from './AssignmentStage';
import { setState, toggle } from '../../redux/scoreSlice';
import FieldWorkStage from './FieldWorkStage';

const CaseInformation = () => {
  const dispatch = useDispatch();
  const { reviewId } = useParams();
  const [caseData, setCaseData] = useState({
    reviewId: '',
    groupName: '',
    division: '',
    role: '',
    assignedTo: '',
  })
  const [loading, setLoading] = useState(true);
  const [action, setAction] = useState('');
  const [planning, setPlanning] = useState('');
  const [fieldwork, setFieldwork] = useState('');
  const ApiToken = localStorage.getItem("authToken");
  const [isFieldDisabled, setIsFieldDisabled] = useState(true);
  const [isCompleted, setIsCompleted] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModelOpenSubmit,setIsModelOpenSubmit] = useState(false);
  const [documentMesage, setDocumentMessage] = useState('');
  const [documentExist, setDocumentExist] = useState(false);
  const [actionOptions, setActionOptions] = useState([]);
  const { isActive } = useSelector((state) => state.Score.isActive);
  const [documentPresent, setDocumentPresent] = useState(false);

  // const [role, setRole] = useState('');
  const [isReadonlyPT, setIsReadOnlyPT] = useState(false);
  const [isReadonlyAS, setIsReadonlyAS] = useState(false);

  let role = localStorage.getItem('role');
  console.log("setRole", role);

  const currentStatus = localStorage.getItem('currentStatus');
  console.log("currentStatus for readonly", currentStatus);

  useEffect(() => {
    console.log("role:", role);
    console.log("currentStatus:", currentStatus);
    if (role === "HeadofPPC") {
      setIsReadOnlyPT(true);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    } else if (role === "SrCreditReviewer" && currentStatus === "Approve" && planning === "PlanningCompleted") {
      setIsReadOnlyPT(true);
      setIsReadonlyAS(false);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    }
    else if (role === "SrCreditReviewer" && currentStatus === "Reject" && planning === "PlanningCompleted") {
      setIsReadOnlyPT(false);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    }
    else if (role === "SrCreditReviewer" && currentStatus === "null" && planning !== "null") {
      setIsReadOnlyPT(false);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    }


  }, [role, planning, currentStatus])

  // const handleSetActive = (value) => {
  //   dispatch(setState(value));
  // }


  //// Document

  const [rows, setRows] = useState([]);
  useEffect(() => {
    if (reviewId) {
      fetchData(reviewId);
    } else {
      console.log("ReviewId is missing");

    }
  }, [reviewId])

  const fetchData = async (reviewId) => {
    try {
      const response = await axios.get(
        `http://localhost:9195/api/query/file/findByReviewId/${reviewId}`,
        {
          headers: {
            Authorization: `Bearer ${ApiToken}`,
          },
        }
      );
      if (Array.isArray(response.data.result)) {
        setRows(response.data.result);
        console.log("Fetched rows: ", response.data.result);
        if (response.data.result.length > 0) {
          setDocumentPresent(true);
          console.log("document set to True");
        } else {
          setDocumentPresent(false);
          console.log("document set To False in length = 0", documentPresent);
        }


      } else {
        console.error("Expected an array, but received:", response.data);
        setRows([]);
        setDocumentPresent(false);
        console.log("document set To False");


      }
    } catch (error) {
      console.error("Error fetching data", error);
      setRows([]);
      setDocumentPresent(false);
      console.log("document set To False");

    }
  };
  //// End Document

  //// AssignmentStage
  const [data, setData] = useState([]);
  const [selectedUser, setSelectedUser] = useState('');
  console.log("selectedUser in CI", selectedUser);

  useEffect(() => {
    CreditReviewer();
  }, [])

  const CreditReviewer = async () => {

    let url = "http://localhost:1992/auth/getUserByCreditReviewerRole";

    try {
      const response = await axios.get(url, {
        headers: {
          'Content-Type': 'application/json',
        }
      });
      if (response.data.status === 200) {
        const resultData = response.data.result;
        console.log("result", resultData);
        setData(resultData);
      } else {
        console.log("Users not found with CreditReviewer");
      }

    } catch (error) {
      console.log("Error while Fetching User with CreditReviwer Role");

    }
  }

  /// End AssignementStage




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
    const role = localStorage.getItem('role');
    // const planning = localStorage.getItem('planning');
    // const action = localStorage.getItem('currentStatus');
    console.log("action current status ", action);


    if (action === "") {
      showToast("Select Action before Submit");
    } else {
      console.log("documentPresent", documentPresent);
      if (!documentPresent) {
        setDocumentMessage("Please add a document");
        showToast("Please add a document");
      } else {
        if (action === "AssigntoCreditReviewer") {
          if (selectedUser == null || selectedUser === "") {
            showToast("Select Credit Reviewer");
          } else {
            setDocumentMessage('');
            setIsModelOpenSubmit(true);
            // UpdateDetails();
            // setTimeout(() => {
            //   window.close();
            // }, 5000);
          }
        } else {
          setDocumentMessage('');
          setIsModelOpenSubmit(true);
          // UpdateDetails();
          // setTimeout(() => {
          //   window.close();
          // }, 5000);
        }

      }
    }

    // if (role === "SrCreditReviewer" && planning === "PlanningCompleted" && action === "Approve") {
    //   if (selectedUser == null || selectedUser === "") {
    //     showToast("Select Credit Reviewer");
    //   } else {
    //     if (action === "") {
    //       showToast("Select Action before Submit");
    //     } else {
    //       if (!documentPresent) {
    //         setDocumentMessage("Please add a document");
    //         showToast("Please add a document");

    //       } else {
    //         setDocumentMessage('');
    //         UpdateDetails();
    //         setTimeout(() => {
    //           window.close();
    //         }, 5000);
    //       }
    //     }
    //   }
    // } else {
    //   if (action === "") {
    //     showToast("Select Action before Submit");
    //   } else {
    //     if (!documentPresent) {
    //       setDocumentMessage("Please add a document");
    //       showToast("Please add a document");

    //     } else {
    //       setDocumentMessage('');
    //       UpdateDetails();
    //       setTimeout(() => {
    //         window.close();
    //       }, 5000);
    //     }
    //   }
    // }
  }

  
  const handleModelConfirmSubmit = () => {
    UpdateDetails();
    setIsModelOpenSubmit(false);
    setTimeout(() => {
      window.close();
    }, 5000);
  }

  const handleModalCancelSubmit = () => {
    setIsModelOpenSubmit(false);
  };

  // const handleDocumentsFetched = (exists) => {
  //   setDocumentExist(exists);
  // }
  const UpdateDetails = async () => {
    const role = localStorage.getItem("role");
    const assignedTo = localStorage.getItem("assignedTo");

    console.log("role", role);
    console.log("assignedTo", assignedTo);

    const inputs = {
      reviewId: reviewId,
      action: action,
      role: role,
      planning: planning,
      fieldwork: fieldwork,
      assignedTo: selectedUser
    }
    console.log("action", action);
    console.log("inputs", inputs);

    const token = localStorage.getItem('authToken');
    try {
      const response = await axios.put("http://localhost:9195/api/action/submitTask", inputs, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        }
      })
      if (response.status === 200) {
        console.log("Data updated Successfully");
        showToastSuccess(response.data.message);
        dispatch(setState(true));
        localStorage.setItem('isActive', true);

        localStorage.setItem('planning', '');
        localStorage.setItem('action', '');
        localStorage.setItem("role", response.data.result.role);
        console.log("role", response.data.result.role);
        console.log("assignedTo", response.data.result.assignedTo);
        localStorage.setItem('assignedTo', 'null');
        console.log("on Update assignedTo is null");


        setAction('');
        setPlanning('');
        setIsCompleted(true);
        setDocumentExist(false);

        console.log("planning After Submit", planning);
        console.log("action After Submit", action);

      } else {
        console.log("Failed To Update");
        setAction('');
        setPlanning('');
      }
    } catch (error) {
      console.log("Error updating Data", error);
      setAction('');
      setPlanning('');
    }
  }


  const handleValueChange = (e) => {
    const value = e.target.value;
    setPlanning(value);
    if (value !== "") {
      setIsFieldDisabled(true);

    }
  };

  const handleEditClick = () => {
    setIsModalOpen(true);
  };

  const handleModalConfirm = () => {
    setIsFieldDisabled(false);
    setIsModalOpen(false);
  };

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  const handleSaveAndClose = () => {

    localStorage.setItem('planning', planning);
    localStorage.setItem('action', action);
    localStorage.setItem('selectedUser', selectedUser);

    console.log('Saving planning to localStorage:', localStorage.getItem('planning'));
    console.log('Saving action to localStorage:', localStorage.getItem('action'));
    console.log('Saving selectedUser to localStorage', localStorage.getItem('selectedUser'));
    window.close();
  }
  useEffect(() => {
    const savedPlanning = localStorage.getItem('planning');
    const savedAction = localStorage.getItem('action');
    const savedSelectedUser = localStorage.getItem('selectedUser');


    if (savedPlanning) {
      console.log("savedPlanning", savedPlanning);      
      setPlanning(savedPlanning);
    }
    if (savedAction) {
      console.log("savedAction", savedAction);
      setAction(savedAction);
    }
    if (savedSelectedUser) {
      console.log("savedSelectedUser", savedSelectedUser);
      setSelectedUser(savedSelectedUser);
    }
  }, []);

  useEffect(() => {
    console.log("Updated Planning State:", planning);
    console.log("local storage planning : ", localStorage.getItem('planning'))
  }, [planning]);


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
          assignedTo: data.assignedTo,
          action: action
        });
        localStorage.setItem("reviewId", data.reviewId);
        localStorage.setItem("role", data.role);
        // localStorage.setItem("assignedTo", data.assignedTo);
        localStorage.setItem("planning", data.planning);
        setPlanning(data.planning);
        console.log("setPlanning", data.planning);
        localStorage.setItem('currentStatus', data.action);


        if (data.role === "SrCreditReviewer" && data.planning === null) {
          console.log("Setting options for Sr CreditReviewer and empty planning");

          setActionOptions([
            { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" }
          ]);
        } else if (data.role === "HeadofPPC" && data.planning === "PlanningCompleted") {
          console.log("Setting options for Head of PPC");
          setActionOptions([
            { value: "Approve", label: "Approve" },
            { value: "Reject", label: "Reject" }
          ]);
        } else if (data.role === "SrCreditReviewer" && data.planning === "PlanningCompleted" && data.action === "Approve") {
          console.log("Setting options for Sr CreditReviewer and PlanningCompleted");
          setActionOptions([
            { value: "AssigntoCreditReviewer", label: "Submit to Credit Reviewer" }
          ]);
        }
        else if (data.role === "SrCreditReviewer" && data.planning === "PlanningCompleted" && data.action === "Reject") {
          console.log("After Reject");

          setActionOptions([
            { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" }
          ]);
        }

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
              value={caseData.assignedTo}
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
              {/* <MenuItem value="">
                <em>None</em>
              </MenuItem> */}
              {actionOptions.length > 0 ? (
                actionOptions.map((option, index) => (
                  <MenuItem key={index} value={option.value}>
                    {option.label}
                  </MenuItem>
                ))
              ) : (
                <MenuItem value="">No Option avaible</MenuItem>
              )}
              {/* <MenuItem value="SubmittedToHeadofPPC">Submit to Head of PPC</MenuItem> */}
            </TextField>
          </div>
          <div className='submitTACI'>
            <Button variant='contained' sx={{ backgroundColor: '#1B4D3E' }}
              onClick={handleSubmit}
              disabled={planning !== 'PlanningCompleted'}
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
          <Dialog open={isModalOpen} onClose={handleModalCancel} sx={{ marginBottom: '190px' }}>
            <DialogTitle sx={{ color: 'black', fontWeight: 'bold' }}>Confirm Change</DialogTitle>
            <DialogContent>
              <DialogContentText sx={{ color: 'black', fontWeight: '600' }}>
                Do you want to change the planning?
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button variant='contained' onClick={handleModalCancel} sx={{ backgroundColor: '#1B4D3E' }}>
                No
              </Button>
              <Button variant='contained' onClick={handleModalConfirm} sx={{ backgroundColor: '#1B4D3E' }}>
                Yes
              </Button>
            </DialogActions>
          </Dialog>
        </div>
        <div className='SaveAndCloseButtonCI'>
          <Button variant='contained'
            sx={{ backgroundColor: '#1B4D3E' }}
            onClick={handleSaveAndClose}
          >Save & Close</Button>
        </div>
      </div>
      <div className='AssignmentStage'>
        <AssignmentStage
          data={data}
          selectedUser={selectedUser}
          setSelectedUser={setSelectedUser}
          readOnly={isReadonlyAS}
        />
      </div>
      <div className='planningTabsDiv'>
        <PlanningTabs
          documentMesage={documentMesage}
          fetchData={fetchData}
          rows={rows}
          setRows={setRows}
          readOnly={isReadonlyPT}
        />
      </div>
      <div className='planningTabsDiv'>
        <FieldWorkStage
        />
      </div>
      <Dialog open={isModelOpenSubmit} onClose={handleModalCancelSubmit} sx={{ marginBottom: '190px' }}>
          <DialogTitle sx={{ color: 'black', fontWeight: 'bold', width:'400px', height:'250' }}>Confirm Change</DialogTitle>
          <DialogContent>
            <DialogContentText sx={{ color: 'black', fontWeight: '600' }}>
              Do you want to submit ?
            </DialogContentText>
          </DialogContent>
          <DialogActions>
            <Button variant='contained' onClick={handleModalCancelSubmit} sx={{ backgroundColor: '#1B4D3E' }}>
              No
            </Button>
            <Button variant='contained' onClick={handleModelConfirmSubmit} sx={{ backgroundColor: '#1B4D3E' }}>
              Yes
            </Button>
          </DialogActions>
        </Dialog>
    </div>

  )
}

export default CaseInformation
