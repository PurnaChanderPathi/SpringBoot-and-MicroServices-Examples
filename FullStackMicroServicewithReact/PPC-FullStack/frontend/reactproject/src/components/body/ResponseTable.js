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
  // const isViewAndUpload = useSelector((state) => state.scope.isViewAndUpload);

  // const handleViewAndUpload = () => {
  //         dispatch(setViewAndUpload(true));
  // }

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
        <Typography variant="body1" sx={{ color: "white", marginRight: 1 }}>
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
    </Box>
  );
};

export default ResponseTable;
