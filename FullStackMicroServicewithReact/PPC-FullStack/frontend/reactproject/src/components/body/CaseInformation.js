import React, { useEffect, useState } from "react";
import "./CaseInformation.css";
import { useParams } from "react-router-dom";
import PlanningTabs from "./PlanningStage";
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Divider,
  FormControl,
  InputLabel,
  MenuItem,
  Modal,
  Select,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import { useDispatch, useSelector } from "react-redux";
import AssignmentStage from "./AssignmentStage";
import {
  setEmptyState,
  setMyReloadPage,
  setSelectedChildReview,
  setState,
} from "../../redux/scoreSlice";
import FieldWorkStage from "./FieldWorkStage";
import MashreqHeader from "../header/MashreqHeader";
import ResponseAndRemediationStage from "./ResponseAndRemediationStage";
import Swal from "sweetalert2";
import SPOC from "./SPOC";
import CloseIcon from "@mui/icons-material/Close";
import { getMyTaskTableFetchDetails } from "../../redux/MyTaskMultiTableSearch";
import FooterScreen from "../footer/FooterScreen";
import axiosInstance from "../axios/axiosInstance";

const CaseInformation = () => {
  const dispatch = useDispatch();
  const { reviewId } = useParams();
  const reviewIds = localStorage.getItem("reviewId");
  const [caseData, setCaseData] = useState({
    reviewId: "",
    groupName: "",
    division: "",
    role: "",
    assignedTo: "",
    createdDate: "",
    currentStatus: "",
  });
  const [loading, setLoading] = useState(true);
  const [action, setAction] = useState("");
  const [planning, setPlanning] = useState("");
  const [fieldwork, setFieldwork] = useState("");
  const ApiToken = localStorage.getItem("authToken");
  const [isFieldDisabled, setIsFieldDisabled] = useState(false);
  const [isCompleted, setIsCompleted] = useState(true);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isModelOpenSubmit, setIsModelOpenSubmit] = useState(false);
  const [documentMesage, setDocumentMessage] = useState("");
  const [documentExist, setDocumentExist] = useState(false);
  const [actionOptions, setActionOptions] = useState([]);
  const isActive = useSelector((state) => state.Score.isActive);
  const [documentPresent, setDocumentPresent] = useState(false);
  const isEmpty = useSelector((state) => state.Score.isEmpty);
  const isChildReviewSelected = useSelector(
    (state) => state.Score.isChildReviewSelected
  );
  const childReviewId = localStorage.getItem("childReviewId");
  const token = localStorage.getItem("authToken");
  const reviewType = localStorage.getItem("reviewType");
  const ChildReviewIdName = "ChildReviewId";
  const ReviewIdName = "ReviewId";

  // const [role, setRole] = useState('');
  const [isReadonlyPT, setIsReadOnlyPT] = useState(false);
  const [isReadonlyAS, setIsReadonlyAS] = useState(false);

  let role = localStorage.getItem("role");
  console.log("setRole", role);

  const currentStatus = localStorage.getItem("currentStatus");
  console.log("currentStatus for readonly", currentStatus);

  const DivisionToSpoc = caseData.division;
  const GroupNameToSpoc = caseData.groupName;
  console.log(
    ` GroupName & Division in Caseinformation Stage ", ${GroupNameToSpoc} && ${DivisionToSpoc}`
  );
  const username = localStorage.getItem("username");
  const reviewStatus = localStorage.getItem("reviewStatus");

  const formatDate = (dateString) => {
    const date = new Date(dateString);

    if (isNaN(date)) {
      return "Invalid Date";
    }

    const options = {
      weekday: "long",
      year: "numeric",
      month: "long",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      hour12: true,
    };

    return new Intl.DateTimeFormat("en-US", options).format(date);
  };

  useEffect(() => {
    if (role === "CreditReviewer") {
      dispatch(setEmptyState(false));
      dispatch(setState(false));
      dispatch(setSelectedChildReview(false));
    }
  }, [role]);

  useEffect(() => {
    if (role === "SPOC") {
      setPlanning("PlanningCompleted");
      dispatch(setEmptyState(true));
      dispatch(setState(true));
      dispatch(setSelectedChildReview(true));
      setDocumentPresent(true);
    } else if (role === "CreditReviewer" && reviewStatus === "in-Progress") {
      setPlanning("PlanningCompleted");
      setDocumentPresent(true);
      // setSelectedUser(username);
    } else if (role === "SrCreditReviewer" && reviewStatus === "in-Progress") {
      setDocumentPresent(true);
      setPlanning("PlanningCompleted");
    }
  });

  useEffect(() => {
    if (fieldwork === "FieldWorkCompleted") {
      setActionOptions([
        { value: "FieldWorkCompleted", label: "FieldWork Completed" },
      ]);
    } else {
      setActionOptions([
        { value: "SubmittedToHeadofPPC", label: "Submitted To Head of PPC" },
      ]);
    }
  }, [fieldwork]);

  useEffect(() => {
    console.log("isEmpty in CI", isEmpty);
    console.log("isActive in CI", isActive);
    console.log("isSelected Radio button", isChildReviewSelected);
  });

  useEffect(() => {
    console.log("role:", role);
    console.log("currentStatus:", currentStatus);
    if (role === "HeadofPPC") {
      setIsReadOnlyPT(true);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    } else if (
      role === "SrCreditReviewer" &&
      currentStatus === "Approve" &&
      planning === "PlanningCompleted"
    ) {
      setIsReadOnlyPT(true);
      setIsReadonlyAS(false);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    } else if (
      role === "SrCreditReviewer" &&
      currentStatus === "Reject" &&
      planning === "PlanningCompleted"
    ) {
      setIsReadOnlyPT(false);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    } else if (
      role === "SrCreditReviewer" &&
      currentStatus === "null" &&
      planning !== "null"
    ) {
      setIsReadOnlyPT(false);
      setIsReadonlyAS(true);
      console.log("isReadonlyAS", isReadonlyAS);
      console.log("isReadonlyPT", isReadonlyPT);
    }
  }, [role, planning, currentStatus]);

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
  }, [reviewId]);

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
  const [selectedUser, setSelectedUser] = useState("");
  console.log("selectedUser in CI", selectedUser);

  useEffect(() => {
    CreditReviewer();
  }, []);

  const CreditReviewer = async () => {
    let url = "http://localhost:1992/auth/getUserByCreditReviewerRole";

    try {
      const response = await axios.get(url, {
        headers: {
          "Content-Type": "application/json",
        },
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
  };

  /// End AssignementStage

  const showToast = (message) => {
    Swal.fire({
      icon: "error",
      // title: 'Oops...',
      text: message,
      position: "bottom-left",
      toast: true,
      timer: 5000,
      showConfirmButton: false,
      didClose: () => Swal.close(),
      customClass: {
        popup: "swal-toast-popup",
      },
      background: "red",
      color: "white",
      height: "10%",
    });
  };

  const showToastSuccess = (message) => {
    Swal.fire({
      icon: "success",
      // title: 'Oops...',
      text: message,
      position: "top-right",
      toast: true,
      timer: 5000,
      showConfirmButton: false,
      didClose: () => Swal.close(),
      customClass: {
        popup: "swal-toast-popup",
      },
      background: "Green",
      color: "white",
    });
  };

  const handleSubmit = () => {
    const role = localStorage.getItem("role");
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
            setDocumentMessage("");
            setIsModelOpenSubmit(true);
          }
        } else if (
          action === "SubmittoSPOC" ||
          action === "SubmittoCreditReviewer"
        ) {
          console.log("isActive in CI", isActive);

          if (
            isActive === false ||
            isEmpty === false ||
            isChildReviewSelected === false
          ) {
            showToast("Add Qurty before Submit");
          } else {
            setDocumentMessage("");
            setIsModelOpenSubmit(true);
          }
        } else {
          setDocumentMessage("");
          setIsModelOpenSubmit(true);
        }
      }
    }
  };

  const handleModelConfirmSubmit = () => {
    if (
      role === "CreditReviewer" ||
      role === "SPOC" ||
      (role === "SrCreditReviewer" && fieldwork === "FieldWorkCompleted")
    ) {
      UpdateObligorSpoc();
    } else {
      UpdateDetails();
    }
    setIsModelOpenSubmit(false);
    setTimeout(() => {
      dispatch(getMyTaskTableFetchDetails(username, token));
      window.close();
    }, 5000);
  };

  const handleModalCancelSubmit = () => {
    setIsModelOpenSubmit(false);
  };

  // const handleDocumentsFetched = (exists) => {
  //   setDocumentExist(exists);
  // }
  const UpdateObligorSpoc = async () => {
    let url = "http://localhost:9195/api/ActionObligor/update/Obligor";
    console.log(
      `Body at UpdateObligorSpoc reviewId : ${reviewIds}, childReviewId: ${childReviewId}, action : ${action}, role :${role}, selectedUser : ${selectedUser}`
    );
    let input = {
      action: action,
      reviewId: reviewIds,
      role: role,
      childReviewId: childReviewId,
      assignedTo: selectedUser,
      fieldwork: fieldwork,
    };
    console.log("Final input payload before API call:", input);
    console.log("Action value:", action);
    const normalizedAction = action.trim();
    console.log("Normalized Action value:", normalizedAction);

    if (normalizedAction.toLowerCase() === "submittocreditreviewer") {
      const CreditReviewer = localStorage.getItem("createdBy");
      input.assignedTo = CreditReviewer || selectedUser;
    } else if (normalizedAction.toLowerCase() === "submittosrcreditreviewer") {
      input.assignedTo = "";
    }
    try {
      const response = await axios.put(url, input, {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "application/json",
        },
      });
      if (response.data.status === 200) {
        const data = response.data.result;
        console.log("Obligor Updated Successfully ", data);
        showToastSuccess("SuccessFully Submitted..!");
        localStorage.setItem("taskStatus", data.taskStatus);
        if (role === "CreditReviewer") {
          localStorage.setItem("createdBy", username);
          console.log(
            `After Obligor Submit isEmpty : ${isEmpty} && isActive : ${isActive} && : isChildReviewSelected ${isChildReviewSelected} `
          );
        }
        setTimeout(() => {
          dispatch(getMyTaskTableFetchDetails(username, token));
        }, 2000);
        setSelectedUser("");
      } else if (response.data.status === 404) {
        console.log("Failed to update obligor");
      }
    } catch (error) {
      console.log("Failed to Process Obligor Update");
    }
  };

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
      assignedTo: selectedUser,
    };
    console.log("action", action);
    console.log("inputs", inputs);

    try {
      const response = await axios.put(
        "http://localhost:9195/api/action/submitTask",
        inputs,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
        }
      );
      if (response.status === 200) {
        console.log("Submited Successfully");
        showToastSuccess("SuccessFully Submitted..!");
        dispatch(setState(true));
        setTimeout(() => {
          dispatch(getMyTaskTableFetchDetails(username, token));
        }, 3000);
        localStorage.setItem("isActive", true);
        localStorage.setItem("planning", "");
        localStorage.setItem("action", "");
        localStorage.setItem("role", response.data.result.role);
        console.log("role", response.data.result.role);
        console.log("assignedTo", response.data.result.assignedTo);
        localStorage.setItem("assignedTo", "null");
        localStorage.setItem("selectedUser", "");
        console.log("on Update assignedTo is null");
        setAction("");
        setPlanning("");
        setSelectedUser("");
        setIsCompleted(true);
        setDocumentExist(false);

        console.log("planning After Submit", planning);
        console.log("action After Submit", action);
      } else {
        console.log("Failed To Update");
        setAction("");
        setPlanning("");
      }
    } catch (error) {
      console.log("Error updating Data", error);
      setAction("");
      setPlanning("");
    }
  };

  const handleValueChange = (e) => {
    setIsFieldDisabled(true);
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
    let reviewId = localStorage.getItem("reviewId");

    console.log("reviewId", reviewId);

    let inputs = {
      reviewId: reviewId,
      planning: planning,
    };

    try {
      const response = await axios.put(
        "http://localhost:9195/api/action/update",
        inputs,
        {
          headers: {
            Authorization: `Bearer ${ApiToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.data.status === 200) {
        console.log("updated Data", response.data.message);
      } else {
        console.log("Failed to Update Details");
      }
    } catch (error) {
      console.log("Error while processing to update Details", error.message);
    }
  };

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  const handleSaveAndClose = () => {
    // localStorage.setItem('planning', planning);
    localStorage.setItem("action", action);
    localStorage.setItem("selectedUser", selectedUser);
    updateBySAS();
    console.log(
      "Saving planning to localStorage:",
      localStorage.getItem("planning")
    );
    console.log(
      "Saving action to localStorage:",
      localStorage.getItem("action")
    );
    console.log(
      "Saving selectedUser to localStorage",
      localStorage.getItem("selectedUser")
    );

    setTimeout(() => {
      window.close();
    }, 2000);
  };
  useEffect(() => {
    // const savedPlanning = localStorage.getItem('planning');
    const savedAction = localStorage.getItem("action");
    const savedSelectedUser = localStorage.getItem("selectedUser");

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
    console.log("local storage planning : ", localStorage.getItem("planning"));
  }, [planning]);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const url = `/api/query/${reviewId}?reviewType=${reviewType}`;
        console.log("url in fetchData ", url);

        const response = await axiosInstance.get(url, {
          headers: {
            Authorization: `Bearer ${ApiToken}`,
            "Content-Type": "application/json",
          },
        });
        if (response.data.status === 200) {
          const data = response.data.result;
          console.log("data in fetchData ", data);
          if (reviewType === "childReviewId") {
            setCaseData({
              reviewId: data.childReviewId,
              groupName: data.groupName,
              division: data.division,
              role: data.role,
              assignedTo: data.assignedTo,
              action: action,
              createdDate: data.createdOn,
              currentStatus: data.taskStatus,
            });
          } else {
            setCaseData({
              reviewId: data.reviewId,
              groupName: data.groupName,
              division: data.division,
              role: data.role,
              assignedTo: data.assignedTo,
              action: action,
              createdDate: data.createdDate,
              currentStatus: data.currentStatus,
            });
          }

          localStorage.setItem("reviewId", data.reviewId);
          localStorage.setItem("role", data.role);
          localStorage.setItem("planning", data.planning);
          setPlanning(data.planning);
          console.log("setPlanning", data.planning);
          localStorage.setItem("currentStatus", data.action);
          localStorage.setItem("reviewStatus", data.reviewStatus);

          if (data.role === "SrCreditReviewer" && data.planning === null) {
            console.log(
              "Setting options for Sr CreditReviewer and empty planning"
            );

            setActionOptions([
              { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" },
            ]);
          } else if (
            data.role === "SrCreditReviewer" &&
            data.planning === "PlanningCompleted" &&
            data.action === null
          ) {
            console.log(
              "Setting options for Sr CreditReviewer and empty planning"
            );
            setActionOptions([
              { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" },
            ]);
          } else if (
            data.role === "HeadofPPC" &&
            data.planning === "PlanningCompleted" &&
            data.action === "SubmittedToHeadofPPC"
          ) {
            console.log("Setting options for Head of PPC");
            setActionOptions([
              { value: "Approve", label: "Approve" },
              { value: "Reject", label: "Reject" },
            ]);
          } else if (
            data.role === "SrCreditReviewer" &&
            data.planning === "PlanningCompleted" &&
            data.action === "Approve"
          ) {
            console.log(
              "Setting options for Sr CreditReviewer and PlanningCompleted"
            );
            setActionOptions([
              {
                value: "AssigntoCreditReviewer",
                label: "Submit to Credit Reviewer",
              },
            ]);
          } else if (
            data.role === "SrCreditReviewer" &&
            data.planning === "PlanningCompleted" &&
            data.action === "Reject"
          ) {
            console.log("After Reject");

            setActionOptions([
              { value: "SubmittedToHeadofPPC", label: "Submit to Head of PPC" },
            ]);
          } else if (
            data.role === "CreditReviewer" &&
            data.planning === "PlanningCompleted"
          ) {
            setActionOptions([
              { value: "SubmittoSPOC", label: "Submit to SPOC" },
              {
                value: "SubmittoSrCreditReviewer",
                label: "Submit to SrCreditReviewer",
              },
            ]);
          } else if (data.role === "SPOC") {
            setActionOptions([
              {
                value: "SubmittoCreditReviewer",
                label: "Submit to CreditReviewer",
              },
            ]);
          } else if (
            data.role === "CreditReviewer" &&
            data.reviewStatus === "in-Progress"
          ) {
            setPlanning("PlanningCompleted");
            setActionOptions([
              { value: "SubmittoSPOC", label: "Submit to SPOC" },
              {
                value: "SubmittoSrCreditReviewer",
                label: "Submit to SrCreditReviewer",
              },
            ]);
          } else if (
            data.role === "SrCreditReviewer" &&
            data.reviewStatus === "in-Progress"
          ) {
            setPlanning("PlanningCompleted");
          }
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [reviewId, ApiToken]);

  if (loading) {
    return <div>Loading...</div>;
  }

  const asmtAction = localStorage.getItem("currentStatus");
  return (
    <div className="CaseInfoMainDiv">
      <div className="MashreqHeader">
        <MashreqHeader />
      </div>
      <ToastContainer />
      <div className="CaseInfoScreen">
        <div className="CaseInfoScreen1">
          <div className="CaseInfoheading">
            <Typography sx={{ fontWeight: "550" }}>
              <span
                style={{
                  textDecoration: "underline",
                  textDecorationThickness: "4px",
                  textDecorationColor: "#FF5E00",
                  textUnderlineOffset: "4px",
                }}
                className="underlineText"
              >
                Case
              </span>{" "}
              Details
            </Typography>
          </div>
          <div className="CDCT">
            <div className="fetchCaseInformation">
              <div className="CaseDetailsfetch">
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">
                    {/* ReviewId */}
                    {reviewType === "childReviewId"
                      ? ChildReviewIdName
                      : ReviewIdName}
                  </div>
                  <div className="ReviewInputCS">
                    {/* <input type='text' value={caseData.reviewId}
                    className='inputReviewCS'
                    disabled
                  /> */}
                    <Typography>{caseData.reviewId}</Typography>
                  </div>
                </div>
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">GroupName</div>
                  <div className="ReviewInputCS">
                    <Typography>{caseData.groupName}</Typography>
                    {/* <input type='text'
                    value={caseData.groupName}
                    className='inputReviewCS'
                    disabled
                  /> */}
                  </div>
                </div>
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">Division</div>
                  <div className="ReviewInputCS">
                    {/* <input type='text'
                    value={caseData.division}
                    className='inputReviewCS'
                    disabled
                  /> */}
                    <Typography>{caseData.division}</Typography>
                  </div>
                </div>
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">Role</div>
                  <div className="ReviewInputCS">
                    {/* <input type='text'
                    value={caseData.role}
                    className='inputReviewCS' disabled
                  /> */}
                    <Typography>{caseData.role}</Typography>
                  </div>
                </div>
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">PPC Initiator</div>
                  <div className="ReviewInputCS">
                    {/* <input type='text'
                    value={caseData.assignedTo}
                    className='inputReviewCS' disabled
                  /> */}
                    <Typography>{caseData.assignedTo}</Typography>
                  </div>
                </div>
                <div className="ReviewIdinfoCS">
                  <div className="ReviewLabelCS">Case status</div>
                  <div className="ReviewInputCS">
                    {/* <input type='text'
                    value={caseData.role}
                    className='inputReviewCS' disabled
                  /> */}
                    <Typography>{caseData.currentStatus}</Typography>
                  </div>
                </div>
              </div>
              <div className="DividerCIF">
                <hr />
              </div>

              <div>
                <div className="CaseCreatedDateCIF">
                  <div
                    style={{
                      width: "185px",
                      paddingTop: "8px",
                      fontWeight: "bold",
                    }}
                  >
                    CASE CREATED DATE :
                  </div>
                  <div>
                    <input
                      type="text"
                      value={formatDate(caseData.createdDate)}
                      className="inputReviewDate"
                      disabled
                    />
                  </div>
                </div>
              </div>
            </div>
            <div className="ClassTimeLineDiv">{/* Hello */}</div>
          </div>
        </div>
      </div>
      <div className="PlanningMainDivCI">
        <div className="planningMainDiv">
          <div className="PlanningStageCmptd">
            <div className="TakeActionSubmitCS">
              <FormControl
                fullWidth
                variant="outlined"
                disabled={planning !== "PlanningCompleted"}
                sx={{
                  width: "200px",
                  height: "40px",
                }}
              >
                <InputLabel
                  htmlFor="GroupName"
                  sx={{
                    textAlign: "center",
                    lineHeight: "15px",
                  }}
                >
                  TakeAction
                </InputLabel>
                <Select
                  label="TakeAction"
                  id="GroupName"
                  value={action}
                  onChange={(e) => setAction(e.target.value)}
                  sx={{
                    "& .MuiOutlinedInput-notchedOutline": {
                      borderColor: "black",
                    },
                    "&:hover .MuiOutlinedInput-notchedOutline": {
                      borderColor: "#FF5E00",
                    },
                    "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                      borderColor: "#FF5E00 !important",
                    },
                    "& .MuiInputLabel-root.Mui-focused": { color: "black" },
                    width: "100%",
                    height: "100%",
                  }}
                  MenuProps={{
                    PaperProps: {
                      sx: {
                        maxHeight: 300,
                      },
                    },
                  }}
                >
                  {actionOptions.length > 0 ? (
                    actionOptions.map((option, index) => (
                      <MenuItem key={index} value={option.value}>
                        {option.label}
                      </MenuItem>
                    ))
                  ) : (
                    <MenuItem value="">No Option available</MenuItem>
                  )}
                </Select>
              </FormControl>
            </div>
            <div className="submitTACI">
              <Button
                variant="contained"
                sx={{
                  backgroundColor: "#FF5E00",
                  textAlign: "center",
                }}
                onClick={handleSubmit}
                disabled={planning !== "PlanningCompleted"}
              >
                Submit
              </Button>
            </div>
          </div>
          {role !== "SPOC" ? (
            <div className="planningEditScreen">
              <FormControl
                fullWidth
                variant="outlined"
                disabled={isFieldDisabled && planning !== ""}
                sx={{
                  width: "250px",
                  height: '45px',
                  paddingLeft: '10px'
                }}
              >
                <InputLabel
                  htmlFor="GroupName"
                  sx={{
                    textAlign: "center",
                    lineHeight: "15px",
                  }}
                >
                  Planning
                </InputLabel>
                <Select
                  label="Planning"
                  id="GroupName"
                  value={planning}
                  onChange={handleValueChange}
                  sx={{
                    "& .MuiOutlinedInput-notchedOutline": {
                      borderColor: "black",
                    },
                    "&:hover .MuiOutlinedInput-notchedOutline": {
                      borderColor: "#FF5E00",
                    },
                    "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                      borderColor: "#FF5E00 !important",
                    },
                    "& .MuiInputLabel-root.Mui-focused": { color: "black" },
                    width: '100%',
                    height: '90%'
                  }}
                  MenuProps={{
                    PaperProps: {
                      sx: {
                        maxHeight: 300,
                      },
                    },
                  }}
                >
                  <MenuItem value="PlanningCompleted">
                    Planning Completed
                  </MenuItem>
                  <MenuItem value="Planninginprogress">
                    Planning in progress
                  </MenuItem>
                </Select>
              </FormControl>

              <Button
                className="BtnEditCI"
                variant="contained"
                sx={{
                  backgroundColor: "#FF5E00",
                  height: "30px",
                  width: "70px",
                }}
                onClick={() => setIsFieldDisabled(false)}
              >
                Edit
              </Button>
              {/* <Dialog open={isModalOpen} onClose={handleModalCancelSubmit} sx={{ marginBottom: '190px' }}>
                  <div className='loadingScreen' style={{
                    width: '500px', height: '240px',
                    display: 'flex', flexDirection: 'column', border: '1px solid #B2BEB5'
                  }}>
                    <div className='loadingHeader' style={{
                      height: '20vh', display: 'flex',
                      justifyContent: 'space-between', alignItems: 'center', borderBottom: '1px solid #B2BEB5', backgroundColor: 'whitesmoke', paddingLeft: "15px"
                    }}>
                      <Typography
                        sx={{ fontWeight: 'bold' }}>
                        <span style={{
                          textDecoration: 'underline',
                          textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                          textUnderlineOffset: '4px'
                        }}
                          className='underlineText'>Con</span>firm Change
                      </Typography>
                      <Button onClick={handleModalCancel}><CloseIcon sx={{ color: 'black' }} /></Button>
                    </div>
                    <div className='loader' style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', height: '80vh', backgroundColor: 'white' }}>
                      <Typography
                        sx={{ fontWeight: 'bold' }}>
                        <span style={{
                          textDecoration: 'underline',
                          textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                          textUnderlineOffset: '4px'
                        }}
                          className='underlineText'>Do</span> you want to change the planning?
                      </Typography>
                    </div>
                    <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                      <Button onClick={handleModalConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                        Yes
                      </Button>
                    </div>
                  </div>
                </Dialog> */}
            </div>
          ) : null}
          {role !== "SPOC" ? (
            <div className="SaveAndCloseButtonCI">
              <Button
                variant="contained"
                sx={{
                  backgroundColor: "#FF5E00",
                  width: "140px",
                  height: "30px",
                  fontSize: "small",
                }}
                onClick={handleSaveAndClose}
              >
                Save & Close
              </Button>
            </div>
          ) : null}

          {role === "SrCreditReviewer" && asmtAction === "Approve" && (
            <div className="AssignmentStage">
              <AssignmentStage
                data={data}
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}
                // readOnly={isReadonlyAS}
              />
            </div>
          )}

          {(role === "SrCreditReviewer" && planning === "PlanningCompleted") ||
          role === "CreditReviewer" ? (
            <div className="planningTabsDiv">
              <FieldWorkStage
                DivisionToSpoc={DivisionToSpoc}
                GroupNameToSpoc={GroupNameToSpoc}
              />
            </div>
          ) : null}

          {(role === "SrCreditReviewer" && planning === "PlanningCompleted") ||
          role === "CreditReviewer" ||
          role === "SPOC" ? (
            <div className="planningTabsDiv">
              <ResponseAndRemediationStage />
            </div>
          ) : null}

          {role === "CreditReviewer" ? (
            <div>
              <SPOC
                GroupNameToSpoc={GroupNameToSpoc}
                DivisionToSpoc={DivisionToSpoc}
                selectedUser={selectedUser}
                setSelectedUser={setSelectedUser}
              />
            </div>
          ) : null}

          {role === "SrCreditReviewer" && planning === "PlanningCompleted" && (
            <div className="planningTabsDiv">
              <TextField
                label="Field Work"
                className="GroupNameText"
                id="GroupName"
                select
                value={fieldwork}
                onChange={(e) => setFieldwork(e.target.value)}
                sx={{
                  width: "300px",
                  textAlign: "center",
                  paddingLeft: "10px",
                  "& .MuiOutlinedInput-root": {
                    "& fieldset": {
                      borderColor: "#FF5E00;",
                    },
                    "&:hover fieldset": {
                      borderColor: "#FF5E00",
                    },
                    "&.Mui-focused fieldset": {
                      borderColor: "#FF5E00",
                    },
                  },
                }}
              >
                <MenuItem value="FieldWorkCompleted">
                  FieldWork Completed
                </MenuItem>
                <MenuItem value="FieldWorkinprogress">
                  FieldWork inprogress
                </MenuItem>
              </TextField>
            </div>
          )}

          {role !== "SPOC" && (
            <div className="planningTabsDiv">
              <PlanningTabs
                documentMesage={documentMesage}
                fetchData={fetchData}
                rows={rows}
                setRows={setRows}
                // readOnly={isReadonlyPT}
              />
            </div>
          )}
          <Dialog open={isModelOpenSubmit} sx={{ marginBottom: "190px" }}>
            <div
              className="loadingScreen"
              style={{
                width: "500px",
                height: "240px",
                display: "flex",
                flexDirection: "column",
                border: "1px solid #B2BEB5",
              }}
            >
              <div
                className="loadingHeader"
                style={{
                  height: "20vh",
                  display: "flex",
                  justifyContent: "space-between",
                  alignItems: "center",
                  borderBottom: "1px solid #B2BEB5",
                  backgroundColor: "whitesmoke",
                  paddingLeft: "15px",
                }}
              >
                <Typography sx={{ fontWeight: "bold" }}>
                  <span
                    style={{
                      textDecoration: "underline",
                      textDecorationThickness: "4px",
                      textDecorationColor: "#FF5E00",
                      textUnderlineOffset: "4px",
                    }}
                    className="underlineText"
                  >
                    Con
                  </span>
                  firm Change
                </Typography>
                <Button onClick={handleModalCancelSubmit}>
                  <CloseIcon sx={{ color: "black" }} />
                </Button>
              </div>
              <div
                className="loader"
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  height: "80vh",
                  backgroundColor: "white",
                }}
              >
                <Typography sx={{ fontWeight: "bold" }}>
                  <span
                    style={{
                      textDecoration: "underline",
                      textDecorationThickness: "4px",
                      textDecorationColor: "#FF5E00",
                      textUnderlineOffset: "4px",
                    }}
                    className="underlineText"
                  >
                    Do
                  </span>{" "}
                  You Want To Submit ?
                </Typography>
              </div>
              <div
                style={{
                  display: "flex",
                  justifyContent: "end",
                  alignItems: "center",
                  margin: "20px",
                }}
              >
                <Button
                  onClick={handleModelConfirmSubmit}
                  variant="contained"
                  size="small"
                  sx={{ backgroundColor: "#FF5E00", fontSize: "11px" }}
                >
                  Submit
                </Button>
              </div>
            </div>
          </Dialog>
        </div>
      </div>
      <div className="FooterScreen">
        <FooterScreen />
      </div>
    </div>
  );
};

export default CaseInformation;
