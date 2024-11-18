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
} from "@mui/material";
import React, { useState, useEffect } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import "./Document.css";
import UploadIcon from "@mui/icons-material/Upload";
import axios from "axios";
import OpenInNewIcon from "@mui/icons-material/OpenInNew";

const Document = () => {
    const [open, setOpen] = useState(false);
    const [comment, setComment] = useState("");
    const [input, setInput] = useState(null);
    const [rows, setRows] = useState([]);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [currentPage, setCurrentPage] = useState(1);
    const [fileSelected, setFileSelected] = useState(false); 
    const [pdfOpen, setPdfOpen] = useState(false);
    const [pdfUrl, setPdfUrl] = useState(null);

    const Token = localStorage.getItem("authToken");
    const reviewId = localStorage.getItem("reviewId");


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

    useEffect(() => {
        if (reviewId) {
            fetchData(reviewId);
        }
    }, [reviewId]);

    const fetchData = async (reviewId) => {
        try {
            const response = await axios.get(
                `http://localhost:9195/api/query/file/findByReviewId/${reviewId}`,
                {
                    headers: {
                        Authorization: `Bearer ${Token}`,
                    },
                }
            );

            if (Array.isArray(response.data.result)) {
                setRows(response.data.result);
                console.log("Fetched rows: ", response.data.result);
            } else {
                console.error("Expected an array, but received:", response.data);
                setRows([]);
            }
        } catch (error) {
            console.error("Error fetching data", error);
            setRows([]);
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
        setInput(e.target.files[0]);
        setFileSelected(true);
    };

    const handleCommentChange = (e) => {
        setComment(e.target.value);
    };

    const documentUpload = async () => {

        console.log("reviewId:", reviewId); // Log reviewId
        console.log("comment:", comment); // Log comment
        console.log("input file:", input); // Log the file

        // Validate before uploading
        if (!reviewId || !comment || !input) {
            alert("Please ensure all fields are filled out.");
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
            <Accordion>
                <AccordionSummary
                    sx={{
                        backgroundColor: "rgb(37, 74, 158)",
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
                        <button className="BrowseButtonAD" onClick={handleOpen}>
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
                                            backgroundColor: "rgb(37, 74, 158)",
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
                                            sx={{ width: "510px", border: "rgb(37, 74, 158)" }}
                                            value={comment}
                                            onChange={handleCommentChange}
                                        />
                                        <div className="UCButtonsDOC">
                                            <Button
                                                startIcon={<UploadIcon />}
                                                className="UoloadBDOC"
                                                sx={{
                                                    backgroundColor: "rgb(37,74,158)",
                                                    color: "white",
                                                }}
                                                onClick={documentUpload}
                                            >
                                                Upload
                                            </Button>
                                            <Button
                                                className="CloseBDOC"
                                                sx={{
                                                    backgroundColor: "rgb(37,74,158)",
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
                                        sx={{ backgroundColor: "rgb(37, 74, 158)", color: "white" }}
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
                                                    <Button onClick={()=> handlePdfOpen(row.fileId)}>
                                                        <OpenInNewIcon />
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
        </div>
    );
};

export default Document;
