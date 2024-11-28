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
} from "@mui/material";
import React, { useState, useEffect } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./Document.css";
import UploadIcon from "@mui/icons-material/Upload";
import axios from "axios";
import OpenInNewIcon from "@mui/icons-material/OpenInNew";
import DeleteOutlineIcon from '@mui/icons-material/DeleteOutline';
import { useDispatch, useSelector } from "react-redux";
import { toggle } from "../../redux/scoreSlice";
import { toast, ToastContainer } from "react-toastify";

const Document = ({ documentMesage,fetchData, rows,setRows , readOnly }) => {
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

    const dispatch = useDispatch();
    const {isAction} = useSelector((state) => state.Score);

    const handleToggle = () => {
        dispatch(toggle());
    }

    useEffect(() => {
        if(fetchData){
            console.log("fetchData function is passed as a prop");
            fetchData(reviewId);        
        }
    },[reviewId])

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
            const response = await axios.get(`http://localhost:9195/api/query/file/View/${fileId}`, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json',
                },
                responseType: "blob",
            });
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

    }

    const deleteRecord = async (fileId) => {
        try {
            const response = await axios.delete(`http://localhost:9195/api/file/delete/${fileId}`, {
                headers: {
                    'Authorization': `Bearer ${Token}`,
                    'Content-Type': 'application/json',
                }
            });
            if (response.status === 200) {
                console.log("File Deleted Successfully...!");
                fetchData();
            } else {
                console.log('File deletion failed:', response.data.message || 'Unknown error');
            }
        } catch (error) {
            console.error('Error deleting file:', error.message || error);
        }
    }

    // useEffect(() => {
    //     if (reviewId) {
    //         fetchData(reviewId);
    //     } else {
    //         console.log("ReviewId is missing");

    //     }
    // }, [reviewId])

    // const fetchData = async (reviewId) => {
    //     try {
    //         const response = await axios.get(
    //             `http://localhost:9195/api/query/file/findByReviewId/${reviewId}`,
    //             {
    //                 headers: {
    //                     Authorization: `Bearer ${Token}`,
    //                 },
    //             }
    //         );
    //         if (Array.isArray(response.data.result)) {
    //             setRows(response.data.result);
    //             console.log("Fetched rows: ", response.data.result);
    //             onDocumentsFetched(response.data.result.length > 0);
    //             console.log("onDocumentsFetched",onDocumentsFetched);
    //             if(response.data.result.length > 0){
    //                 handleToggle();
    //                 console.log("isAction",isAction);
                    
    //             }

                    
    //         } else {
    //             console.error("Expected an array, but received:", response.data);
    //             setRows([]);
    //             onDocumentsFetched(false);
    //         }
    //     } catch (error) {
    //         console.error("Error fetching data", error);
    //         setRows([]);
    //         onDocumentsFetched(false);
    //     }
    // };





    const handleOpen = () => setOpen(true);
    const handleClose = () => {
        setOpen(false);
        setInput(null);
        setComment("");
        setFileSelected(false);
    };

    const handleFileChange = (e) => {
        setInput(e.target.files[0]);
        setFileSelected(true);
    };

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };

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

      const handleDelete = () => {
        fetchData(reviewId);
        console.log("onClick Deleted Called fetch Document endpoint");
        
      }

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
                        backgroundColor: "#1B4D3E",
                        color: "white",
                        borderTopLeftRadius: "5px",
                        borderTopRightRadius: "5px",
                    }}
                    expandIcon={<ExpandMoreIcon sx={{ color: "white" }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    Upload Document
                </AccordionSummary>
                <AccordionDetails className="DocumentAD">
                    <div className="DcumentADMain">
                        <button className="BrowseButtonAD" onClick={handleOpen} disabled={readOnly}>
                            Browse
                        </button>
                        <Modal
                            open={open}
                            onClose={handleClose}
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
                                            backgroundColor: "#1B4D3E",
                                            color: "white",
                                            borderTopLeftRadius: "5px",
                                            borderTopRightRadius: "5px",
                                        }}
                                        expandIcon={<ExpandMoreIcon sx={{ color: "white" }} />}
                                        aria-controls="panel1-content"
                                        id="panel1-header"
                                    >
                                        Please select a file
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
                                                border: "#1B4D3E",
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
                                            value={comment}
                                            onChange={handleCommentChange}
                                        />
                                        <div className="UCButtonsDOC">
                                            <Button
                                                startIcon={<UploadIcon />}
                                                className="UoloadBDOC"
                                                sx={{
                                                    backgroundColor: "#1B4D3E",
                                                    color: "white",
                                                }}
                                                onClick={documentUpload}
                                            >
                                                Upload
                                            </Button>
                                            <Button
                                                className="CloseBDOC"
                                                sx={{
                                                    backgroundColor: "#1B4D3E",
                                                    color: "white",
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

                        <Modal open={pdfOpen} onClose={handlePdfClose}>
                            <Box
                                sx={{
                                    width: "80%",
                                    height: "80%",
                                    backgroundColor: "white",
                                    margin: "auto",
                                    padding: "20px",
                                    borderRadius: "8px",
                                    display: "flex",
                                    justifyContent: "center",
                                    alignItems: "center",
                                }}
                            >
                                {pdfUrl && (
                                    <embed
                                        src={pdfUrl}
                                        width="100%"
                                        height="100%"
                                        type="application/pdf"
                                    />
                                )}
                            </Box>
                        </Modal>

                        <div>
                            <TableContainer
                                component={Paper}
                                sx={{ backgroundColor: "white", marginTop: 0 }}
                            >
                                <Table
                                    sx={{ minWidth: 650, borderCollapse: "collapse" }}
                                    aria-label="simple table"
                                >
                                    <TableHead
                                        sx={{ backgroundColor: "#1B4D3E", color: "white" }}
                                    >
                                        <TableRow>
                                            <TableCell
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                fileId
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                reviewId
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                fileName
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                fileType
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                comment
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                size
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                View Document
                                            </TableCell>
                                            <TableCell
                                                align="right"
                                                sx={{ color: "white", border: "1px solid black" }}
                                            >
                                                Delete
                                            </TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {displayedRows.map((row) => (
                                            <TableRow
                                                key={row.reviewId}
                                                sx={{ backgroundColor: "white" }}
                                            >
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.fileId}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.reviewId}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.fileName}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.fileType}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.comment}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    {row.size}
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    <Button onClick={() => handlePdfOpen(row.fileId)}>
                                                        <Tooltip title="Open Document">
                                                            <OpenInNewIcon sx={{ color: '#1B4D3E' }} />
                                                        </Tooltip>
                                                    </Button>
                                                </TableCell>
                                                <TableCell
                                                    align="right"
                                                    sx={{ border: "1px solid black" }}
                                                >
                                                    <Button onClick={() => deleteFile(row.fileId, row.reviewId)} >
                                                        <Tooltip title="Delete">
                                                            <DeleteOutlineIcon sx={{ color: '#1B4D3E' }} />
                                                        </Tooltip>
                                                    </Button>
                                                </TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </div>
                    </div>
                </AccordionDetails>
            </Accordion>
            {/* <div>
                <TableBody>
                    {displayedRows.length > 0 ? (
                        displayedRows.map((row) => (
                            <TableRow key={row.reviewId} sx={{ backgroundColor: "white" }}>
                            </TableRow>
                        ))
                    ) : (
                        <TableRow>
                            <TableCell colSpan={8} align="center">No documents found for this review ID.</TableCell>
                        </TableRow>
                    )}
                </TableBody>
            </div>
            <div>
            {rows.length > 0 && (
                <div>
                </div>
            )}
        </div> */}
        </div>
    );
};

export default Document;
