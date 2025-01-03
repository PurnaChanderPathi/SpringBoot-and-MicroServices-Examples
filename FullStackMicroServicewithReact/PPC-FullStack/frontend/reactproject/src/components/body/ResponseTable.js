import {
  Box,
  Button,
  colors,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Modal,
  Paper,
  Radio,
  RadioGroup,
  styled,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextareaAutosize,
  TextField,
  Tooltip,
  Typography,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import ArrowUpwardIcon from "@mui/icons-material/ArrowUpward";
import DeleteIcon from "@mui/icons-material/Delete";
import PreviewIcon from "@mui/icons-material/Preview";
import LibraryAddIcon from "@mui/icons-material/LibraryAdd";
import { useDispatch, useSelector } from "react-redux";
import DriveFolderUploadIcon from '@mui/icons-material/DriveFolderUpload';
import EditOffIcon from '@mui/icons-material/EditOff';
import {
  getResponseRemediationDetailsByReviewId,
  setRowsPerPage,
  setTotalPages,
} from "../../redux/ResponseRemedaitionSlice";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import axios from "axios";
import { useDropzone } from "react-dropzone";
import CloseIcon from "@mui/icons-material/Close";
import NoteAddIcon from "@mui/icons-material/NoteAdd";
import ResponseQueryTable from "./ResponseQueryTable";
import { toast, ToastContainer } from "react-toastify";
import Swal from "sweetalert2";
import { getResponseQueryFetchDetails } from "../../redux/ResponseQueryFetchDetails";
import { setChildReviewId, setSelectedChildReview, setViewAndUpload } from "../../redux/scoreSlice";
import ReplyIcon from "@mui/icons-material/Reply";
import ObligorAndResponseViewAndUpload from "./ObligorAndResponseViewAndUpload";
import OblogorDocumentTable from "./OblogorDocumentTable";

const ResponseTable = () => {
  const dispatch = useDispatch();
  const { rows, totalPages, rowsPerPage, error, loading } = useSelector(
    (state) => state.Response
  );
  const [currentPage, setCurrentPage] = React.useState(1);
  const reviewId = localStorage.getItem("reviewId");
  const token = localStorage.getItem("authToken");
  const createdBy = localStorage.getItem("username");
  const childReviewId = localStorage.getItem("childReviewId");
  const [selectedReviewId, setSelectedReviewId] = useState(null);
  const Token = localStorage.getItem("authToken");
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [isChildReviewId, setIsChildReviewId] = React.useState(null);
  const [openResponseQuery, setOpenResponseQuery] = useState(false);
  const [isInserted, setIsInserted] = useState(false);
  const role = localStorage.getItem("role");
  const [isQueryRendered, setIsQueryRendered] = useState(role !== "SPOC");
const [isTableRendered, setIsTableRendered] = useState(true);
    const ApiToken = localStorage.getItem('authToken');
    const [ObligorDetails, setObligorDetails] = useState(null);
    const [fileName, setFileName] = useState('');
    const [file, setFile] = useState(null);
    const [isUploading, setIsUploading] = useState(false);
    const [uploadMessage, setUploadMessage] = useState('');
        const [ObligorDocument, setObligorDocument] = useState(null);
  // const isViewAndUpload = useSelector((state) => state.scope.isViewAndUpload);

  // const handleViewAndUpload = () => {
  //         dispatch(setViewAndUpload(true));
  // }

      const onDrop = (acceptedFiles) => {
          const file = acceptedFiles[0];
          if (file) {
              setFileName(file.name);
              setFile(file);
          }
      };
  
      const { getRootProps, getInputProps } = useDropzone({
          onDrop,
          accept: '.pdf,.doc,.docx,.jpg,.png,.jfif',
          maxFiles: 1
      });
  
      const [open, setOpen] = React.useState(false);

       const handleOpen = () => setOpen(true);
          const handleClose = () => {
              setOpen(false);
              // dispatch(setViewAndUpload(false));
              // dispatch(setChildReviewId(''));
              setInput({
                  Obligor: '',
                  Division: '',
                  Cifid: '',
                  PremId: '',
              });
          }
      

  

  useEffect(() => {
    if (role === "SPOC") {
      setSelectedReviewId(reviewId);
    }
  });

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
    });
  };
  const isActive = useSelector((state) => state.Score.isActive);

  const handleOpenResponseQuery = () => {
    console.log("isActive in ResponseTable :", isActive);
    console.log("selectedReviewId :", selectedReviewId);

    if (selectedReviewId !== null && isActive === true) {
      setOpenResponseQuery(true);
    } else {
      showToast("Select ChildReviewId & SPOC");
    }
  };
  const handleCloseResponseQuery = () => {
    setOpenResponseQuery(false);
    setInput((prevInput) => ({
      ...prevInput,
      query: "",
    }));
  };

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  const handleModalConfirm = () => {
    if (isChildReviewId) {
      deleteResponseByChildReviewId(isChildReviewId);
      setIsModalOpen(false);
    }
  };
  const isChildReviewSelected = useSelector(
    (state) => state.Score.isChildReviewSelected
  );

  const handleRadioChange = (childReviewId) => {
    setSelectedReviewId(childReviewId);
    console.log("selectedReviewId", selectedReviewId);
    dispatch(setSelectedChildReview(true));
    console.log("Selected Child Radio button", isChildReviewSelected);
  };

  const [input, setInput] = useState({
    query: "",
    createdBy: "",
    reviewId: "",
    childReviewId: "",
  });

  const updateResponseRemediationWithChildReviewId = async () => {
    if (input.Obligor !== null || input.Division !== null || input.Cifid !== null || input.PremId !== null) {
        const inputs = {
            reviewId: reviewId,
            obligorName: input.Obligor,
            division: input.Division,
            obligorCifId: input.Cifid,
            obligorPremId: input.PremId,
            childReviewId: childReviewId
        }
        console.log("inputs in updateResponseRemediationWithChildId", input);

        let url = "http://localhost:9195/api/ActionObligor/updateResponseRemediation";
        try {
            const response = await axios.put(url, inputs, {
                headers: {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });
            if (response.data.status === 200) {
                console.log("updated ResponseRemediation successfully...!");
                console.log("result on update ResponseRemediation", response.data.result);
                setInput({
                    Obligor: '',
                    Division: '',
                    Cifid: '',
                    PremId: '',
                });
                handleClose();
                // dispatch(setViewAndUpload(false));
                // dispatch(setChildReviewId(''));
                dispatch(getResponseRemediationDetailsByReviewId(reviewId, ApiToken));
            } else if (response.data.status === 404) {
                console.log(" Failed to updated ResponseDemediation");
                setInput({
                    Obligor: '',
                    Division: '',
                    Cifid: '',
                    PremId: '',
                });
                // dispatch(setViewAndUpload(false));
                // dispatch(setChildReviewId(''));
            }
        } catch (error) {
            console.log("Error while the processing update", error.message);
            setInput({
                Obligor: '',
                Division: '',
                Cifid: '',
                PremId: '',
            });
            // dispatch(setViewAndUpload(false));
            // dispatch(setChildReviewId(''));
        }
    } else {
        showToast("Please Fill Details to Update");
    }
}
const handleDeleteDoc = (obligorId) => {

    handleObligorDocDelete(obligorId);
    setTimeout(() => {
        getObligorDocumentByReviewId();
    }, 500)
}

const handleObligorDocDelete = async (obligorId) => {

    let url = `http://localhost:9195/api/ActionObligor/deleteDoc/${obligorId}`;

    try {
        const response = await axios.delete(url, {
            headers: {
                'Authorization': `Bearer ${ApiToken}`,
                'Content-Type': 'application/json',
            }
        });
        if (response.data.status === 200) {
            console.log("obligorDoc Deleted Successfully...!");
        } else {
            console.log("File Deletion Failed");

        }
    } catch (error) {
        console.log("Error deleting file :", error.message);

    }
}

const getObligorDetailsByReviewId = async () => {

  const reviewId = localStorage.getItem("reviewId");
  console.log("reviewId at Obligor get function", reviewId);


  const url = `http://localhost:9195/api/QueryObligor/getObligorDetailsByReviewId?reviewId=${reviewId}`;

  try {
      const response = await axios.get(url, {
          headers: {
              'Authorization': `Bearer ${ApiToken}`,
              'Content-Type': 'application/json',
          }
      });

      if (response.data.status === 200) {
          setObligorDetails(response.data.result);
          console.log("getObligorDetailsByReviewId", ObligorDetails);
      } else if (response.data.status === 404) {
          setObligorDetails(null);
      } else {
          console.log("data not found with reviewId :", reviewId);
      }

  } catch (error) {
      console.log("Error fetching Obligor Details with reviewId :", reviewId);

  }

}
const getObligorDocumentByReviewId = async () => {

  const reviewId = localStorage.getItem("reviewId");
  console.log("reviewId at Obligor get function", reviewId);


  const url = `http://localhost:9195/api/QueryObligor/getObligorDocumentByReviewId?reviewId=${reviewId}`;

  try {
      const response = await axios.get(url, {
          headers: {
              'Authorization': `Bearer ${ApiToken}`,
              'Content-Type': 'application/json',
          }
      });

      if (response.data.status === 200) {
          setObligorDocument(response.data.result);
          console.log("getObligorDocumentByReviewId", ObligorDocument);
      } else if (response.data.status === 404) {
          setObligorDocument(null);
      } else {
          console.log("data not found with reviewId :", reviewId);
      }

  } catch (error) {
      console.log("Error fetching Obligor Document with reviewId :", reviewId);

  }

}

const handleUploadobligorDocument = () => {
  handleDocumentUpload();
}

const handleDocumentUpload = async () => {

  if (!file) {
      setUploadMessage("Please select a file before uploading.");
      console.log("please Select File");
      showToast("please Select File to Upload");

      return;
  }

  setIsUploading(true);
  setUploadMessage("Uploading...");

  const url = "http://localhost:9195/api/ActionObligor/obligorDocument";
  const reviewId = localStorage.getItem("reviewId");
  const uploadedBy = localStorage.getItem("username");

  const formData = new FormData();
  formData.append("reviewId", reviewId);
  formData.append("documentName", fileName);
  formData.append("uploadedBy", uploadedBy);
  formData.append("file", file);

  try {
      const response = await axios.post(url, formData, {
          headers: {
              'Authorization': `Bearer ${ApiToken}`,
              'Content-Type': 'multipart/form-data',
          }
      });

      if (response.data.status === 200) {
          setUploadMessage("File uploaded successfully!");
          setFileName('');
          setFile(null);
          setTimeout(() => {
              getObligorDocumentByReviewId();
          }, 500)
      } else {
          setUploadMessage("Failed to upload file. Please try again.");
          setFileName('');
          setFile(null);
      }
  } catch (error) {
      console.error("Error uploading file: ", error.message);
      setUploadMessage("Error during upload. Please try again.");
      setFileName('');
      setFile(null);
  } finally {
      setIsUploading(false);
      setFileName('');
      setFile(null);
  }
}

const updateObligorWithChildId = async () => {

  if (input.Obligor !== null || input.Division !== null || input.Cifid !== null || input.PremId !== null) {
      const inputs = {
          reviewId: reviewId,
          obligorName: input.Obligor,
          division: input.Division,
          obligorCifId: input.Cifid,
          obligorPremId: input.PremId,
          childReviewId: childReviewId
      }
      console.log("inputs in updateObligorWithChildId", input);

      let url = "http://localhost:9195/api/ActionObligor/updateObligorByChildReviewId";

      try {
          const response = await axios.put(url, inputs, {
              headers: {
                  'Authorization': `Bearer ${ApiToken}`,
                  'Content-Type': 'application/json',
              }
          });

          if (response.data.status === 200) {
              console.log("updated ObligorDetails successfully...!");
              console.log("result on update Obligor", response.data.result);
              setInput({
                  Obligor: '',
                  Division: '',
                  Cifid: '',
                  PremId: '',
              });
              getObligorDetailsByReviewId();
              handleClose();
              // dispatch(setViewAndUpload(false));
              // dispatch(setChildReviewId(''));
          } else if (response.data.status === 404) {
              console.log(" Failed to updated ObligorDetails");
              setInput({
                  Obligor: '',
                  Division: '',
                  Cifid: '',
                  PremId: '',
              });
              // dispatch(setViewAndUpload(false));
              // dispatch(setChildReviewId(''));
          }
      } catch (error) {
          console.log("Error while the processing update", error.message);
          setInput({
              Obligor: '',
              Division: '',
              Cifid: '',
              PremId: '',
          });
          // dispatch(setViewAndUpload(false));
          // dispatch(setChildReviewId(''));
      }
  } else {
      showToast("Please Fill Details to Update");
  }


}

  const ResponseQueryDetailsInsertion = async () => {
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
      });
    };
    if (input.query === "") {
      console.log("Query in ResponseQuery", input.query);
      console.log(
        `username :${createdBy} , reviewId : ${reviewId} , childReviewId: ${childReviewId}`
      );
      showToast("Please Add Query");
    } else {
      setInput((prevInput) => ({ ...prevInput, reviewId: reviewId }));
      setInput((prevInput) => ({ ...prevInput, createdBy: createdBy }));
      setInput((prevInput) => ({ ...prevInput, childReviewId: childReviewId }));
      console.log(
        `ReviewId & token & username & childReviewId at ResponseTanble Review : ${reviewId} & token : ${Token} & username : ${createdBy} & childReviewId : ${childReviewId}`
      );
      console.log("input", input);

      let url = "http://localhost:9195/api/ActionObligor/ResponseQuery/save";

      const requestData = {
        reviewId: input.reviewId,
        query: input.query,
        createdBy: createdBy,
        childReviewId: childReviewId,
      };

      try {
        const response = await axios.post(url, requestData, {
          headers: {
            Authorization: `Bearer ${Token}`,
            "Content-Type": "application/json",
          },
        });
        if (response.data.status === 200) {
          console.log(
            "Response Query Details Saved Successfully...!",
            response.data.message
          );
          // setOpenResponseQuery(false);
          setInput((prevInput) => ({
            ...prevInput,
            query: "",
          }));
          //   setIsInserted(true);
          dispatch(getResponseQueryFetchDetails(childReviewId, Token));
        } else {
          console.log(
            `Failed to insert Response Query Details with body : ${input}`
          );
          setInput((prevInput) => ({
            ...prevInput,
            query: "",
          }));
        }
      } catch (error) {
        console.log("Failed to process the flow ", error.message);
        setInput((prevInput) => ({
          ...prevInput,
          query: "",
        }));
      }
    }
  };

  useEffect(() => {
    console.log("reviewId at ResponseTable", reviewId);
    console.log("token at ResponseTable", token);

    if (reviewId && token) {
      dispatch(getResponseRemediationDetailsByReviewId(reviewId, token));
    }
  }, [dispatch, reviewId, token]);

  useEffect(() => {
    dispatch(setTotalPages(Math.ceil(rows.length / rowsPerPage)));
  }, [rows, rowsPerPage, dispatch]);

  const deleteResponseByChildReviewId = async (childReviewId) => {
    let url = `http://localhost:9195/api/ActionObligor/deleteResponse/${childReviewId}`;

    console.log("token at deleteResponseByChildReviewId", Token);

    try {
      const response = await axios.delete(url, {
        headers: {
          Authorization: `Bearer ${Token}`,
          "Content-Type": "application/json",
        },
      });
      if (response.data.status === 200) {
        console.log(
          "Response Details Deleted with ChildReviewId",
          childReviewId
        );
        const reviewId = localStorage.getItem("reviewId");
        const token = localStorage.getItem("authToken");
        dispatch(getResponseRemediationDetailsByReviewId(reviewId, token));
      } else {
        console.log(
          "Failed to Delete Response Details with ChildReviewId",
          childReviewId
        );
      }
    } catch (error) {
      console.log("Failed to Process ");
    }
  };

  const handleRowsPerPageChange = (event) => {
    const value = Math.max(1, parseInt(event.target.value, 10));
    dispatch(setRowsPerPage(value));
    setCurrentPage(1);
  };

  const startIndex = (currentPage - 1) * rowsPerPage;
  const displayedRows = rows.slice(startIndex, startIndex + rowsPerPage);

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage((prev) => prev + 1);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 1) {
      setCurrentPage((prev) => prev - 1);
    }
  };

  const style = {
    position: "absolute",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 1400,
    bgcolor: "background.paper",
    boxShadow: 24,
    height: "auto"
  };

  const styleViewAndUpload = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 1100,
    bgcolor: 'background.paper',
    boxShadow: 24,
    height: 500,
};

  const getModalHeight = () => {
    let height = 20; 
    if (role !== "SPOC") {
      height += 210; 
    }
    if (isTableRendered) {
      height += 300;
    }
    return height;
  }

  const handleGetAndUpdateObligor = (childReviewId) => {
    console.log("childReviewId in ObligorTable Page :", childReviewId);
    localStorage.setItem("childReviewId", childReviewId);
    getObligorDetailsWithChildReviewId(childReviewId);
    handleOpen();
}

const getObligorDetailsWithChildReviewId = async (childReviewId) => {

  const url = `http://localhost:9195/api/QueryObligor/findByChildReviewId/${childReviewId}`;

  try {
      const response = await axios.get(url, {
          headers: {
              'Authorization': `Bearer ${ApiToken}`,
              'Content-Type': 'application/json',
          }
      });

      if (response.data.status === 200) {
          const result = response.data.result;
          console.log("obligor findByChildReviewId :", result);
          setInput({
              Obligor: result.obligorName || '',
              Division: result.division || '',
              Cifid: result.obligorCifId || '',
              PremId: result.obligorPremId || '',
          });
      } else if (response.data.status === 404) {
          setInput({
              Obligor: '',
              Division: '',
              Cifid: '',
              PremId: '',
          });
      } else {
          console.log("data not found with reviewId :", reviewId);
          setInput({
              Obligor: '',
              Division: '',
              Cifid: '',
              PremId: '',
          });
      }

  } catch (error) {
      console.log("Error fetching Obligor Document with reviewId :", reviewId);
      setInput({
          Obligor: '',
          Division: '',
          Cifid: '',
          PremId: '',
      });

  }

}

  return (
    <Box sx={{ padding: 2 }}>
      <ToastContainer />
      <Box
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          alignItems: "center",
          marginBottom: 0,
          backgroundColor: "white",
          padding: 1,
        }}
      >
        <Typography variant="body1" sx={{ color: "black", marginRight: 1 }}>
          Per page:
        </Typography>
        <TextField
          type="number"
          value={rowsPerPage}
          onChange={handleRowsPerPageChange}
          variant="outlined"
          size="small"
          sx={{
            width: "100px",
            marginRight: 2,
            backgroundColor: "white",
            "& .MuiOutlinedInput-root": {
              "& fieldset": {
                borderColor: "#FF5E00",
              },
              "&:hover fieldset": {
                borderColor: "#FF5E00",
              },
              "&.Mui-focused fieldset": {
                borderColor: "#FF5E00",
              },
            },
          }}
        />
      </Box>
      <TableContainer
        component={Paper}
        sx={{ backgroundColor: "white", black: 0 }}
      >
        <Table
          sx={{ minWidth: 650, borderCollapse: "collapse" }}
          aria-label="simple table"
        >
          <TableHead sx={{ backgroundColor: "white", color: "white" }}>
            <TableRow>
              {role !== "SPOC" ? (
                <TableCell
                  align="right"
                  sx={{
                    color: "black",
                    border: "1px solid #B2BEB5",
                    fontWeight: "bold",
                  }}
                >
                  SELECT
                </TableCell>
              ) : null}

              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                {" "}
                CHILD REVIEW ID <ArrowUpwardIcon />{" "}
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                OBLIGOR NAME
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                PREM ID
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                CIF ID
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                DIVISION
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                REVIEW STATUS
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                CREATED BY
              </TableCell>
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                ADD/VIEW QUERY
              </TableCell>
              {role !== "SPOC" ? (
                <TableCell
                  align="right"
                  sx={{
                    color: "black",
                    border: "1px solid #B2BEB5",
                    fontWeight: "bold",
                  }}
                >
                  DELETE
                </TableCell>
              ) : null}
              <TableCell
                align="right"
                sx={{
                  color: "black",
                  border: "1px solid #B2BEB5",
                  fontWeight: "bold",
                }}
              >
                VIEW/UPLOAD
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {displayedRows.map((row) => (
              <TableRow key={row.reviewId} sx={{ backgroundColor: "white" }}>
                {role !== "SPOC" ? (
                  <TableCell
                    align="center"
                    component="th"
                    scope="row"
                    sx={{ border: "1px solid #B2BEB5" }}
                  >
                    <Radio
                      sx={{
                        color: "#FF5E00",
                        "&.Mui-checked": {
                          color: "#FF5E00",
                        },
                      }}
                      checked={selectedReviewId === row.childReviewId}
                      onChange={() => handleRadioChange(row.childReviewId)}
                    />
                  </TableCell>
                ) : null}

                <TableCell
                  align="center"
                  component="th"
                  scope="row"
                  sx={{ border: "1px solid #B2BEB5" }}
                >
                  {row.childReviewId}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.obligorName}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.obligorPremId}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.obligorCifId}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.division}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.reviewStatus}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  {row.createdBy}
                </TableCell>
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  <Button
                    onClick={() => {
                      handleOpenResponseQuery();
                      localStorage.setItem("childReviewId", row.childReviewId);
                    }}
                  >
                    <Tooltip title="Add/View Query">
                      <LibraryAddIcon sx={{ color: "#FF5E00" }} />
                    </Tooltip>
                  </Button>
                </TableCell>
                {role !== "SPOC" ? (
                  <TableCell
                    align="center"
                    sx={{ border: "1px solid #B2BEB5" }}
                  >
                    <Button
                      onClick={() => {
                        setIsChildReviewId(row.childReviewId);
                        setIsModalOpen(true);
                      }}
                    >
                      <Tooltip title="Delete">
                        <DeleteOutlineIcon sx={{ color: "#FF5E00" }} />
                      </Tooltip>
                    </Button>
                  </TableCell>
                ) : null}
                <TableCell align="center" sx={{ border: "1px solid #B2BEB5" }}>
                  <Button onClick={() => {
                        handleGetAndUpdateObligor(row.childReviewId);
                        dispatch(setViewAndUpload(true));
                        dispatch(setChildReviewId(row.childReviewId));
                  }}>
                    <Tooltip title="View/Upload">
                      <PreviewIcon sx={{ color: "#FF5E00" }} />
                    </Tooltip>
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Box
        sx={{
          display: "flex",
          justifyContent: "flex-end",
          alignItems: "center",
          marginTop: 2,
        }}
      >
        <Button
          variant="contained"
          onClick={handlePreviousPage}
          disabled={currentPage === 1}
          sx={{
            marginRight: 1,
            backgroundColor: "#FF5E00",
            textTransform: "none",
            color: "white",
            "&:hover": { backgroundColor: "#FF5E00" },
          }}
        >
          Previous
        </Button>
        <Typography variant="body1" sx={{ marginX: 2 }}>
          Page {currentPage} of {totalPages}
        </Typography>
        <Button
          variant="contained"
          onClick={handleNextPage}
          disabled={currentPage === totalPages}
          sx={{
            marginRight: 1,
            backgroundColor: "#FF5E00",
            textTransform: "none",
            color: "white",
            "&:hover": { backgroundColor: "#FF5E00" },
          }}
        >
          Next
        </Button>
      </Box>

      <Dialog open={isModalOpen} sx={{ marginBottom: '190px' }}>
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
                className='underlineText'>Do</span> you want to Delete Respone & Remediation ?
            </Typography>
          </div>
          <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
            <Button onClick={handleModalConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
              Yes
            </Button>
          </div>
        </div>
      </Dialog>
      <Modal
        open={openResponseQuery}
        // onClose={handleCloseResponseQuery}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"

      >
        <Box sx={{ ...style,height: getModalHeight()}} >
          <div className="FieldworkSectionModal">
            <div className="FieldWorkHeading">
              <Typography sx={{ fontWeight: "bold" }}>
                <span
                  style={{
                    textDecoration: "underline",
                    textDecorationThickness: "4px",
                    textDecorationColor: "#FF5E00",
                    textUnderlineOffset: "4px",
                    borderBottom: "1px solid #B2BEB5",
                  }}
                  className="underlineText"
                >
                  QU
                </span>
                ERY DETAILS
              </Typography>
              <Button onClick={handleCloseResponseQuery}>
                <CloseIcon />
              </Button>
            </div>
            <div
              className="ResponseQueryScreen"
              style={{
                width: "1340px",
                height: "5px",
                display: "flex",
                flexDirection: "column",
              }}
            >
              {role !== "SPOC" && (
                <div className="ResponseQuery" style={{ width: "100%" }}>
                  <TextField
                    label="Query"
                    value={input.query}
                    multiline
                    minRows={6}
                    variant="outlined"
                    fullWidth
                    onChange={(e) =>
                      setInput({ ...input, query: e.target.value })
                    }
                    style={{
                      borderRadius: "5px",
                    }}
                    InputProps={{
                      style: {
                        padding: "10px",
                      },
                    }}
                    sx={{
                      margin: "30px",
                      "& .MuiOutlinedInput-root": {
                        "& fieldset": {
                          borderColor: "#FF5E00",
                        },
                        "&:hover fieldset": {
                          borderColor: "#FF5E00",
                        },
                        "&.Mui-focused fieldset": {
                          borderColor: "#FF5E00",
                        },
                      },
                      "& .MuiInputLabel-root": {
                        color: "black",
                      },
                      "& .MuiInputLabel-root.Mui-focused": {
                        color: "black",
                      },
                    }}
                  />
                </div>
              )}

              {
                (role !== "SPOC") && (
                  <div
                    className="ResponseQueryBtnAdd"
                    style={{ paddingLeft: "1220px" }}
                  >
                    <Button
                      sx={{ width: "150px" }}
                      startIcon={<NoteAddIcon />}
                      variant="contained"
                      style={{ backgroundColor: "#FF5E00" }}
                      onClick={() => ResponseQueryDetailsInsertion()}
                    >
                      Add
                    </Button>
                  </div>
                ) 
              }

              {
                isTableRendered && (
                  <div
                  className="ResponseQueryTable"
                  style={{ width: "1370px", paddingLeft: "20px" }}
                >
                  <ResponseQueryTable
                    isInserted={isInserted}
                    setIsInserted={setIsInserted}
                  />
                </div>
                )
              }
            </div>
          </div>
        </Box>
      </Modal>
      <Modal
                open={open}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={styleViewAndUpload}>
                    <div className='FieldworkSectionModal'>
                        <div className='FieldWorkHeading'>
                            <Typography
                                sx={{ fontWeight: 'bold' }}>
                                <span style={{
                                    textDecoration: 'underline',
                                    textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                                    textUnderlineOffset: '4px',
                                    borderBottom: "1px solid #B2BEB5"
                                }}
                                    className='underlineText'>FIE</span>LD WORK SECTION
                            </Typography>
                            <Button onClick={handleClose}><CloseIcon /></Button>
                        </div>
                        <div className='FieldWorkObligor'>
                            <div className='FieldWorkStageIP'>

                                <TextField className='Obligor'
                                    label="Obligor"
                                    value={input.Obligor} sx={{
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
                                    onChange={(e) => setInput({ ...input, Obligor: e.target.value })}
                                />

                                <TextField className='Obligor'
                                    label="Division"
                                    value={input.Division} sx={{
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
                                    onChange={(e) => setInput({ ...input, Division: e.target.value })}
                                />

                                <TextField className='Obligor'
                                    label="Cifid"
                                    value={input.Cifid} sx={{
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
                                    onChange={(e) => setInput({ ...input, Cifid: e.target.value })}
                                />

                                <TextField className='Obligor'
                                    label="PremId"
                                    value={input.PremId} sx={{
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
                                    onChange={(e) => setInput({ ...input, PremId: e.target.value })} />

                                <Button
                                    startIcon={<EditOffIcon />} variant='contained' sx={{ backgroundColor: '#093414' }}
                                    onClick={() => {
                                        updateObligorWithChildId();
                                        updateResponseRemediationWithChildReviewId();
                                    }}
                                >UPDATE</Button>
                            </div>
                            <div className='FieldWorkDocument'>
                                <div className='DragAndDropImage'>
                                    <div
                                        {...getRootProps()}
                                        style={{
                                            width: '570px',
                                            height: '100px',
                                            // border: '2px dashed #aaa',
                                            backgroundColor: '#B2BEB5',
                                            borderRadius: '5px',
                                            display: 'flex',
                                            justifyContent: 'center',
                                            alignItems: 'center',
                                            flexDirection: 'column',
                                            textAlign: 'center',
                                            cursor: 'pointer',
                                        }}
                                    >
                                        <input {...getInputProps()} />
                                        <p className='DragAndDropHeading'>
                                            {fileName ? fileName : "Drag & Drop your files or Browse"}</p>
                                    </div>
                                </div>
                                <div className='uploadButtonDAD'>
                                    <Button variant='contained'
                                        sx={{ backgroundColor: '#FF5E00', width: '100px', height: '35px', fontSize: '12px' }}
                                        onClick={() => handleUploadobligorDocument()}
                                        startIcon={<DriveFolderUploadIcon />}>UPLOAD</Button>
                                </div>
                                <div>
                                    <OblogorDocumentTable ObligorDocument={ObligorDocument} handleDeleteDoc={handleDeleteDoc} />
                                </div>
                            </div>

                        </div>

                    </div>


                </Box>
            </Modal>
    </Box>
  );
};

export default ResponseTable;
