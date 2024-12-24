import React, { useEffect, useState } from 'react'
import './CaseInformation.css'
import { useParams } from 'react-router-dom';
import PlanningTabs from './PlanningStage';
import { Alert, Box, Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, MenuItem, Modal, TextField, Typography } from '@mui/material';
import axios from 'axios';
import { ToastContainer, toast } from 'react-toastify';
import { useDispatch, useSelector } from 'react-redux';
import AssignmentStage from './AssignmentStage';
import { setEmptyState, setSelectedChildReview, setState } from '../../redux/scoreSlice';
import FieldWorkStage from './FieldWorkStage';
import MashreqHeader from '../header/MashreqHeader';
import ResponseAndRemediationStage from './ResponseAndRemediationStage';
import Swal from 'sweetalert2';
import SPOC from './SPOC';



const CaseInformation = () => {
  const dispatch = useDispatch();
  const { reviewId } = useParams();
  const reviewIds = localStorage.getItem('reviewId');
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
  const [isFieldDisabled, setIsFieldDisabled] = useState(false);
  const [isCompleted, setIsCompleted] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModelOpenSubmit, setIsModelOpenSubmit] = useState(false);
  const [documentMesage, setDocumentMessage] = useState('');
  const [documentExist, setDocumentExist] = useState(false);
  const [actionOptions, setActionOptions] = useState([]);
  const isActive = useSelector((state) => state.Score.isActive);
  const [documentPresent, setDocumentPresent] = useState(false);
  const isEmpty = useSelector((state) => state.Score.isEmpty);
  const isChildReviewSelected = useSelector((state) => state.Score.isChildReviewSelected);
  const childReviewId = localStorage.getItem("childReviewId");
  const token = localStorage.getItem('authToken');


  // const [role, setRole] = useState('');
  const [isReadonlyPT, setIsReadOnlyPT] = useState(false);
  const [isReadonlyAS, setIsReadonlyAS] = useState(false);

  let role = localStorage.getItem('role');
  console.log("setRole", role);

  const currentStatus = localStorage.getItem('currentStatus');
  console.log("currentStatus for readonly", currentStatus);

  const DivisionToSpoc = caseData.division;
  const GroupNameToSpoc = caseData.groupName;
  console.log(` GroupName & Division in Caseinformation Stage ", ${GroupNameToSpoc} && ${DivisionToSpoc}`);
const username = localStorage.getItem("username");
  useEffect(() => {
    if(role === "SPOC"){
      setPlanning("PlanningCompleted");   
      dispatch(setEmptyState(true));
      dispatch(setState(true));
      dispatch(setSelectedChildReview(true));
      setDocumentPresent(true);
      setSelectedUser(username);
    }
  })

  useEffect(() => {
    console.log("isEmpty in CI",isEmpty);
    console.log("isActive in CI",isActive);
    console.log("isSelected Radio button",isChildReviewSelected);
  })

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
    Swal.fire({
      icon: 'error',
      // title: 'Oops...',
      text: message,
      position: 'bottom-left',
      toast: true,
      timer: 5000,
      showConfirmButton: false,
      didClose: () => Swal.close(),
      customClass: {
        popup: 'swal-toast-popup',
      },
      background: 'red',
      color: 'white',
      height: '10%'
    });
  };

    const showToastSuccess = (message) => {
    Swal.fire({
      icon: 'success',
      // title: 'Oops...',
      text: message,
      position: 'top-right',
      toast: true,
      timer: 5000,
      showConfirmButton: false,
      didClose: () => Swal.close(),
      customClass: {
        popup: 'swal-toast-popup',
      },
      background: 'Green',
      color: 'white',
    });
  };

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

        if (action === "AssigntoCreditReviewer" ) {

          if (selectedUser == null || selectedUser === "") {
            showToast("Select Credit Reviewer");
          } else {
            setDocumentMessage('');
            setIsModelOpenSubmit(true);
          }
        }
        else if(action === "SubmittoSPOC" || action === "SubmittoCreditReviewer"){
          console.log("isActive in CI",isActive);
          
          if(isActive === false || isEmpty === false || isChildReviewSelected === false ){
            showToast("Add Qurty before Submit");
          }else{
            setDocumentMessage('');
            setIsModelOpenSubmit(true);
          }
        }
         else {
          setDocumentMessage('');
          setIsModelOpenSubmit(true);
        }

      }
    }
  }


  const handleModelConfirmSubmit = () => {
    if(role === "CreditReviewer" || role === "SPOC"){
      UpdateObligorSpoc();
    }else{
      UpdateDetails();
    }

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
const UpdateObligorSpoc = async () => {
      let url = "http://localhost:9195/api/ActionObligor/update/Obligor";
      console.log(`Body at UpdateObligorSpoc reviewId : ${reviewIds}, childReviewId: ${childReviewId}, action : ${action}, role :${role}, selectedUser : ${selectedUser}`);
      let input = {
        action : action,
        reviewId : reviewIds,
        role : role,
        childReviewId : childReviewId,
        assignedTo : selectedUser
      };
      console.log("inputs in UpdateObligorSpoc",input);
      
      try {
        const response = await axios.put(url,input,{
          headers : {
            'Authorization': `Bearer ${token}`,
            'Content-Type' : 'application/json',

          }
        });
        if(response.data.status === 200){
          const data = response.data.result;
          console.log("Obligor Updated Successfully ",data);
          showToastSuccess("SuccessFully Submitted..!");
          
        }else if(response.data.status === 404){
            console.log("Failed to update obligor");
            
        }
      } catch (error) {
        console.log("Failed to Process Obligor Update");
        
      }
}

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


    try {
      const response = await axios.put("http://localhost:9195/api/action/submitTask", inputs, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        }
      })
      if (response.status === 200) {
        console.log("Submited Successfully");
        showToastSuccess("SuccessFully Submitted..!");
        dispatch(setState(true));
        localStorage.setItem('isActive', true);

        localStorage.setItem('planning', '');
        localStorage.setItem('action', '');
        localStorage.setItem("role", response.data.result.role);
        console.log("role", response.data.result.role);
        console.log("assignedTo", response.data.result.assignedTo);
        localStorage.setItem('assignedTo', 'null');
        localStorage.setItem('selectedUser', '');
        console.log("on Update assignedTo is null");


        setAction('');
        setPlanning('');
        setSelectedUser('');
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

  const updateBySAS = async () => {
    let reviewId = localStorage.getItem('reviewId');


    console.log("reviewId", reviewId);

    let inputs = {
      reviewId: reviewId,
      planning: planning
    };

    try {
      const response = await axios.put("http://localhost:9195/api/action/update", inputs, {
        headers: {
          'Authorization': `Bearer ${ApiToken}`,
          'Content-Type': 'application/json',
        }
      })
      if (response.data.status === 200) {
        console.log("updated Data", response.data.message);
      } else {
        console.log("Failed to Update Details");
      }
    } catch (error) {
      console.log("Error while processing to update Details", error.message);

    }
  }


  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  const handleSaveAndClose = () => {

    // localStorage.setItem('planning', planning);
    localStorage.setItem('action', action);
    localStorage.setItem('selectedUser', selectedUser);
    updateBySAS();
    console.log('Saving planning to localStorage:', localStorage.getItem('planning'));
    console.log('Saving action to localStorage:', localStorage.getItem('action'));
    console.log('Saving selectedUser to localStorage', localStorage.getItem('selectedUser'));

    setTimeout(() => {
      window.close();
    }, 2000);

  }
  useEffect(() => {
    // const savedPlanning = localStorage.getItem('planning');
    const savedAction = localStorage.getItem('action');
    const savedSelectedUser = localStorage.getItem('selectedUser');


    // if (savedPlanning) {
    //   console.log("savedPlanning", savedPlanning);      
    //   setPlanning(savedPlanning);
    // }
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
        const reviewType = localStorage.getItem('reviewType');
        const url = `http://localhost:9195/api/query/${reviewId}?reviewType=${reviewType}`;
        console.log("url in fetchData ",url);
        
        const response = await axios.get(url, {
          headers: {
            'Authorization': `Bearer ${ApiToken}`,
            'Content-Type': 'application/json',
          }
        });
        if(response.data.status === 200){
          const data = response.data.result;
          console.log("data in fetchData ",data);
          
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
          localStorage.setItem("planning", data.planning);
          setPlanning(data.planning);
          console.log("setPlanning", data.planning);
          localStorage.setItem('currentStatus', data.action);


        if (data.role === "SrCreditReviewer" && data.planning === null) {
          console.log("Setting options for Sr CreditReviewer and empty planning");

          setActionOptions([
            { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" }
          ]);
        }
        else if (data.role === "SrCreditReviewer" && data.planning === "PlanningCompleted" && data.action === null) {
          console.log("Setting options for Sr CreditReviewer and empty planning");
          setActionOptions([
            { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" }
          ]);
        }

        else if (data.role === "HeadofPPC" && data.planning === "PlanningCompleted" && data.action === "SubmittedToHeadofPPC") {
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
        }else if(data.role === "CreditReviewer" && data.planning === "PlanningCompleted"){
          setActionOptions([
            {value: "SubmittoSPOC", label: "Submit to SPOC"},
            {value: "SubmittoSrCreditReviewer", label : "Submit to SrCreditReviewer"}
          ]);
        }
        else if(data.role === "SPOC"){
          setActionOptions([
            {value: "SubmittoCreditReviewer", label : "Submit to CreditReviewer"}
          ])
        }
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


  const asmtAction = localStorage.getItem('currentStatus');
  return (
    <div className='CaseInfoMainDiv'>
      <MashreqHeader />
      <ToastContainer />
      <div className='CaseInfoScreen'>
        <div className='CaseInfoScreen1'>
          <div className='CaseInfoheading'>
            <Typography sx={{ fontWeight: '550' }}>
              <span style={{
                textDecoration: 'underline',
                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                textUnderlineOffset: '4px'
              }}
                className='underlineText'>Case</span> Details
            </Typography>
          </div>
          <div className='CDCT'>
            <div className='fetchCaseInformation'>
              <div className='ReviewIdinfoCS'>
                <div className='ReviewLabelCS'>
                  ReviewId
                </div>
                <div className='ReviewInputCS'>
                  <input type='text' value={caseData.reviewId}
                    className='inputReviewCS'
                    disabled
                  />

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
                    disabled
                     />
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
                    disabled
                     />
                </div>
              </div>
              <div className='ReviewIdinfoCS'>
                <div className='ReviewLabelCS'>
                  Role
                </div>
                <div className='ReviewInputCS'>
                  <input type='text'
                    value={caseData.role}
                    className='inputReviewCS' disabled
                    />
                </div>
              </div>
              <div className='ReviewIdinfoCS'>
                <div className='ReviewLabelCS'>
                  PPC Initiator
                </div>
                <div className='ReviewInputCS'>
                  <input type='text'
                    value={caseData.assignedTo}
                    className='inputReviewCS' disabled
                    />
                </div>
              </div>

            </div>
            <div className='ClassTimeLineDiv'>
              {/* Hello */}
            </div>
          </div>
        </div>
      </div>
      <div className='PlanningMainDivCI'>
        <div className='planningMainDiv'>


          <div className='PlanningStageCmptd'>
            <div className='TakeActionSubmitCS'>
              <TextField
                label="TakeAction"
                // placeholder='TakeAction'
                className='GroupNameText'
                id="GroupName"
                select
                value={action}
                onChange={(e) => setAction(e.target.value)}
                disabled={planning !== 'PlanningCompleted'}
                sx={{
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#FF5E00',
                    },
                    '&:hover fieldset': {
                      borderColor: '#FF5E00',
                    },
                    '&.Mui-focused fieldset': {
                      borderColor: '#FF5E00',
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
              <Button variant='contained' sx={{
                // backgroundColor: '#1B4D3E', height: '30px', paddingBottom: '10px',
                backgroundColor: '#FF5E00', textAlign: 'center'
              }}
                onClick={handleSubmit}
                disabled={planning !== 'PlanningCompleted'}
              >Submit</Button>
            </div>
          </div>
          {
            (role !== "SPOC" ) ? (
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
                    borderColor: '#FF5E00;',
                  },
                  '&:hover fieldset': {
                    borderColor: '#FF5E00',
                  },
                  '&.Mui-focused fieldset': {
                    borderColor: '#FF5E00',
                  },
                },
              }}
            >
              <MenuItem value="PlanningCompleted">Planning Completed</MenuItem>
              <MenuItem value="Planninginprogress">Planning inprogress</MenuItem>
            </TextField>
            <Button className='BtnEditCI'
              variant='contained'
              sx={{ backgroundColor: '#FF5E00', height: '30px', width: '70px' }}
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
                <Button variant='contained' onClick={handleModalCancel} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                  No
                </Button>
                <Button variant='contained' onClick={handleModalConfirm} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                  Yes
                </Button>
              </DialogActions>
            </Dialog>
          </div>
            ) : null 
          }
                    {
            (role !== "SPOC" ) ? (
          <div className='SaveAndCloseButtonCI'>
            <Button variant='contained'
              sx={{ backgroundColor: '#FF5E00', width: '140px', height: '30px', fontSize: 'small' }}
              onClick={handleSaveAndClose}
            >Save & Close</Button>
          </div>
            ): null
          }


          {role === "SrCreditReviewer" && asmtAction === "Approve" && (
            <div className='AssignmentStage'>
              <AssignmentStage
                data={data}
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}
              // readOnly={isReadonlyAS}
              /></div>)}


          {
            (role === "SrCreditReviewer" && planning === "PlanningCompleted") || role === "CreditReviewer" ? (
              <div className='planningTabsDiv'>
                <FieldWorkStage 
                DivisionToSpoc={DivisionToSpoc}
                GroupNameToSpoc={GroupNameToSpoc}
                />
              </div>
            ) : null
          }

          {
            (role === "SrCreditReviewer" && planning === "PlanningCompleted") || role === "CreditReviewer" || role === "SPOC" ? (
              <div className='planningTabsDiv'>
                <ResponseAndRemediationStage />
              </div>
            ) : null
          }

{
            (role === "CreditReviewer" ) ? (
            <div>
              <SPOC GroupNameToSpoc={GroupNameToSpoc}
                DivisionToSpoc={DivisionToSpoc} 
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}/>
            </div>
            ) : null
          }

          {role === "SrCreditReviewer" && planning === "PlanningCompleted" && (
            <div className='planningTabsDiv'>
              <TextField
                label="Field Work"
                className='GroupNameText'
                id="GroupName"
                select
                value={fieldwork}
                onChange={(e) => setFieldwork(e.target.value)}
                sx={{
                  width: '300px',
                  textAlign: 'center',
                  paddingLeft: '10px',
                  '& .MuiOutlinedInput-root': {
                    '& fieldset': {
                      borderColor: '#FF5E00;',
                    },
                    '&:hover fieldset': {
                      borderColor: '#FF5E00',
                    },
                    '&.Mui-focused fieldset': {
                      borderColor: '#FF5E00',
                    },
                  },
                }}
              >
                <MenuItem value="PlanningCompleted">FieldWork Completed</MenuItem>
                <MenuItem value="Planninginprogress">FieldWork inprogress</MenuItem>
              </TextField>
            </div >
          )}
        
        {
          (role !== "SPOC") && (
            <div className='planningTabsDiv'>
            <PlanningTabs
              documentMesage={documentMesage}
              fetchData={fetchData}
              rows={rows}
              setRows={setRows}
            // readOnly={isReadonlyPT}

            />


          </div>
  )
        }






          <Dialog open={isModelOpenSubmit} onClose={handleModalCancelSubmit} sx={{ marginBottom: '190px' }}>
            <DialogTitle sx={{ color: 'black', fontWeight: 'bold', width: '400px', height: '250' }}>Confirm Change</DialogTitle>
            <DialogContent>
              <DialogContentText sx={{ color: 'black', fontWeight: '600' }}>
                Do you want to submit ?
              </DialogContentText>
            </DialogContent>
            <DialogActions>
              <Button variant='contained' onClick={handleModalCancelSubmit} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                No
              </Button>
              <Button variant='contained' onClick={handleModelConfirmSubmit} sx={{ backgroundColor: '#FF5E00', width: '70px', height: '30px' }}>
                Yes
              </Button>
            </DialogActions>
          </Dialog>
        </div>
      </div>
    </div>

  )
}

export default CaseInformation
