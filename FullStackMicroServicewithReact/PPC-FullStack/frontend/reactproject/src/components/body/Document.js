import {
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Box,
  Button,
  Modal,
  Typography,
  TextField,
  TableContainer,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Paper,
  Tooltip,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  DialogActions,
} from "@mui/material";
import React, { useState, useEffect } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./Document.css";
import UploadIcon from "@mui/icons-material/Upload";
import axios from "axios";
import OpenInNewIcon from "@mui/icons-material/OpenInNew";
import DeleteOutlineIcon from "@mui/icons-material/DeleteOutline";
import { toast, ToastContainer } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import Swal from 'sweetalert2';
import CloseIcon from '@mui/icons-material/Close';

const Document = ({ documentMesage, fetchData, rows, setRows, readOnly }) => {
  const [open, setOpen] = useState(false);
  const [comment, setComment] = useState("");
  const [input, setInput] = useState(null);
  // const [rows, setRows] = useState([]);
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [currentPage, setCurrentPage] = useState(1);
  const [fileSelected, setFileSelected] = useState(false);
  const [pdfOpen, setPdfOpen] = useState(false);
  const [pdfUrl, setPdfUrl] = useState(null);
  const Token = localStorage.getItem("authToken");
  const reviewId = localStorage.getItem("reviewId");
  console.log("Review ID:", reviewId);
  const [isModalOpen, setIsModalOpen] = React.useState(false);
  const [docFileId, setDocFileId] = React.useState(null);
  const [docReviewId, setDocReviewId] = useState(null);
  const role = localStorage.getItem('role');

  const handleModalCancel = () => {
    setIsModalOpen(false);
  };

  const handleModalConfirm = () => {
    if (docFileId && docReviewId) {
      deleteFile(docFileId, docReviewId);
      setIsModalOpen(false);
    }
  };

  useEffect(() => {
    if (fetchData) {
      console.log("fetchData function is passed as a prop");
      fetchData(reviewId);
    }
  }, [reviewId]);

  useEffect(() => {
    console.log("Rows have been updated:", rows);
  }, [rows]);

  const handlePdfOpen = (fileId) => {
    fetchPdf(fileId);
  };

  const handlePdfClose = () => {
    setPdfOpen(false);
    setPdfUrl(null);
  };

  const fetchPdf = async (fileId) => {
    try {
      const response = await axios.get(
        `http://localhost:9195/api/query/file/View/${fileId}`,
        {
          headers: {
            Authorization: `Bearer ${Token}`,
            "Content-Type": "application/json",
          },
          responseType: "blob",
        }
      );
      const pdfBlob = response.data;
      const pdfUrl = URL.createObjectURL(pdfBlob);
      setPdfUrl(pdfUrl);
      setPdfOpen(true);
    } catch (error) {
      console.error("Error fetching PDF", error);
      alert("Failed to fetch PDF.");
    }
  };

  const deleteFile = (fileId, reviewId) => {
    console.log("filedId", fileId);
    console.log("reviewId", reviewId);
    deleteRecord(fileId);
    setTimeout(() => {
      fetchData(reviewId);
      console.log("fetchData reloaded");
    }, 200);
  };

  const deleteRecord = async (fileId) => {
    try {
      const response = await axios.delete(
        `http://localhost:9195/api/file/delete/${fileId}`,
        {
          headers: {
            Authorization: `Bearer ${Token}`,
            "Content-Type": "application/json",
          },
        }
      );
      if (response.status === 200) {
        console.log("File Deleted Successfully...!");
        fetchData();
      } else {
        console.log(
          "File deletion failed:",
          response.data.message || "Unknown error"
        );
      }
    } catch (error) {
      console.error("Error deleting file:", error.message || error);
    }
  };

  const handleOpen = () => setOpen(true);
  const handleClose = () => {
    setOpen(false);
    setInput(null);
    setComment("");
    setFileSelected(false);
  };

  const handleFileChange = (e) => {
    const uploadedFile = e.target.files ? e.target.files[0] : {};
    setInput(uploadedFile);
    setFileSelected(true);
  };

  const handleCommentChange = (e) => {
    setComment(e.target.value);
  };

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


  // const handleDelete = () => {
  //     fetchData(reviewId);
  //     console.log("onClick Deleted Called fetch Document endpoint");

  // }

  const documentUpload = async () => {
    console.log("reviewId:", reviewId);
    console.log("comment:", comment);
    console.log("input file:", input);

    if (!reviewId || !comment || !input) {
      // alert("Please ensure all fields are filled out.");
      showToast("Please ensure all fields are filled");

      return;
    }

    const url = "http://localhost:9195/api/file/upload";
    const formData = new FormData();
    formData.append("file", input);
    formData.append("reviewId", reviewId);
    formData.append("comment", comment);

    try {
      const response = await axios.post(url, formData, {
        headers: {
          "Content-Type": "multipart/form-data",
          Authorization: `Bearer ${Token}`,
        },
      });

      console.log("response object", response);
      if (response.status === 200) {
        console.log("File uploaded successfully!");
        handleClose();
        fetchData(reviewId);
      }
    } catch (error) {
      console.log("Failed to upload file", error);
      alert("File upload failed!");
    }
  };

  const startIndex = (currentPage - 1) * rowsPerPage;
  const displayedRows = Array.isArray(rows)
    ? rows.slice(startIndex, startIndex + rowsPerPage)
    : [];

  return (
    <div className="DocumentMain">
      <ToastContainer />
      <Accordion>
        <AccordionSummary
          sx={{
            backgroundColor: "transparent",
            color: "black",
            borderTopLeftRadius: "5px",
            borderTopRightRadius: "5px",
          }}
          expandIcon={<ExpandMoreIcon sx={{ color: "#FF5E00" }} />}
          aria-controls="panel1-content"
          id="panel1-header"
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
              Upl
            </span>
            oad Document
          </Typography>
        </AccordionSummary>
        <AccordionDetails className="DocumentAD">
          <div className="DcumentADMain">
            <button
              className="BrowseButtonAD"
              onClick={handleOpen}
              disabled={readOnly}
            >
              Browse
            </button>
            <Modal
              open={open}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
              sx={{
                display: "flex",
                justifyContent: "center",
                paddingTop: "30px",
                backgroundColor: "rgba(0, 0, 0, 0.5)",
              }}
            >
              <Box
                sx={{
                  width: "550px",
                  height: "350px",
                  backgroundColor: "transparent",
                  borderBottomLeftRadius: "5px",
                  borderTopRightRadius: "5px",
                  padding: "20px",
                }}
              >
                <Accordion>
                  <AccordionSummary
                    sx={{
                      backgroundColor: "transparent",
                      color: "black",
                      borderTopLeftRadius: "5px",
                      borderTopRightRadius: "5px",
                    }}
                    expandIcon={<ExpandMoreIcon sx={{ color: "white" }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
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
                        Plea
                      </span>
                      se select a file
                    </Typography>
                  </AccordionSummary>
                  <AccordionDetails className="DocumentAD">
                    <Typography
                      className="ChooseFileText"
                      sx={{
                        paddingTop: "20px",
                        fontSize: "30px",
                        fontWeight: "bold",
                      }}
                    >
                      Choose File
                    </Typography>
                    <input type="file" onChange={handleFileChange} />
                    <Typography
                      sx={{
                        paddingTop: "35px",
                        fontSize: "13px",
                        fontWeight: "bold",
                      }}
                    >
                      Comment
                    </Typography>
                    <TextField
                      sx={{
                        width: "510px",
                        border: "#FF5E00",
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
                      value={comment}
                      onChange={handleCommentChange}
                    />
                    <div className="UCButtonsDOC">
                      <Button
                        startIcon={<UploadIcon />}
                        className="UoloadBDOC"
                        sx={{
                          backgroundColor: "#FF5E00",
                          color: "white",
                          height: "33px",
                        }}
                        onClick={documentUpload}
                      >
                        Upload
                      </Button>
                      <Button
                        className="CloseBDOC"
                        sx={{
                          backgroundColor: "#FF5E00",
                          color: "white",
                          height: "33px",
                        }}
                        onClick={handleClose}
                      >
                        Close
                      </Button>
                    </div>
                  </AccordionDetails>
                </Accordion>
              </Box>
            </Modal>

            <Modal open={pdfOpen}>
              <Box
                sx={{
                  width: "80%",
                  height: "80%",
                  backgroundColor: "white",
                  margin: "auto",
                  padding: "20px",
                  marginTop: '25px',
                  borderRadius: "8px",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <div style={{width : '100%', height : '100%'}}>
                <div style={{ display : 'flex', justifyContent : 'space-between', alignItems : 'center'}}>
                <Typography
                  sx={{ fontWeight: 'bold' }}>
                  <span style={{
                    textDecoration: 'underline',
                    textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                    textUnderlineOffset: '4px'
                  }}
                    className='underlineText'>Do</span>cument
                </Typography>
                <Button onClick={handlePdfClose}><CloseIcon sx={{ color: 'black' }} /></Button>
                </div>
                <div style={{ width : '100%', height : '95%'}}>
                {pdfUrl && (
                  <embed
                    src={pdfUrl}
                    width="100%"
                    height="100%"
                    type="application/pdf"
                  />
                )}
                </div>
                </div>
                

              </Box>
            </Modal>

            <div>
              <TableContainer
                component={Paper}
                sx={{ backgroundColor: "transparent", marginTop: 0 }}
              >
                <Table
                  sx={{ minWidth: 650, borderCollapse: "collapse" }}
                  aria-label="simple table"
                >
                  <TableHead
                    sx={{ backgroundColor: "transparent", color: "black" }}
                  >
                    <TableRow>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        fileId
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        reviewId
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        fileName
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        fileType
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        comment
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        size
                      </TableCell>
                      <TableCell
                        align="right"
                        sx={{
                          color: "black",
                          border: "1px solid #B2BEB5",
                          fontWeight: "bold",
                        }}
                      >
                        View Document
                      </TableCell>
                      {
                        (role === "SrCreditReviewer") ? (
                          <TableCell
                            align="right"
                            sx={{
                              color: "black",
                              border: "1px solid #B2BEB5",
                              fontWeight: "bold",
                            }}
                          >
                            Delete
                          </TableCell>
                        ) : null
                      }
                    </TableRow>
                  </TableHead>
                  <TableBody>
                    {displayedRows.map((row) => (
                      <TableRow
                        key={row.reviewId}
                        sx={{ backgroundColor: "transparent" }}
                      >
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.fileId}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.reviewId}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.fileName}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.fileType}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.comment}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          {row.size}
                        </TableCell>
                        <TableCell
                          align="right"
                          sx={{ color: "black", border: "1px solid #B2BEB5" }}
                        >
                          <Button onClick={() => handlePdfOpen(row.fileId)}>
                            <Tooltip title="Open Document">
                              <OpenInNewIcon sx={{ color: "#FF5E00" }} />
                            </Tooltip>
                          </Button>
                        </TableCell>

                        {
                          (role === "SrCreditReviewer") ? (
                            <TableCell
                              align="right"
                              sx={{ color: "black", border: "1px solid #B2BEB5" }}
                            >
                              <Button
                                onClick={
                                  () => {
                                    setDocFileId(row.fileId);
                                    setDocReviewId(row.reviewId);
                                    setIsModalOpen(true);
                                  }
                                  // deleteFile(row.fileId, row.reviewId)
                                }
                                disabled={readOnly}
                              >
                                <Tooltip title="Delete" disabled={readOnly}>
                                  <DeleteOutlineIcon
                                    sx={{ color: "#FF5E00" }}
                                    disabled={readOnly}
                                  />
                                </Tooltip>
                              </Button>
                            </TableCell>
                          ) : null
                        }


                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              </TableContainer>
            </div>
          </div>
        </AccordionDetails>
      </Accordion>
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
                    className='underlineText'>Do</span> you want to Delete Document ?
                </Typography>
              </div>
              <div style={{ display: 'flex', justifyContent: 'end', alignItems: 'center', margin: '20px' }}>
                <Button onClick={handleModalConfirm} variant='contained' size='small' sx={{ backgroundColor: '#FF5E00', fontSize: '11px' }}>
                  Yes
                </Button>
              </div>
            </div>
          </Dialog>
    </div>
  );
};

export default Document;
