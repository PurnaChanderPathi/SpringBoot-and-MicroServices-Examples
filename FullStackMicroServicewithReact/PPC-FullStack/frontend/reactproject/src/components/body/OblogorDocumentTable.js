import {
    Box,
    Button,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Select,
    MenuItem,
    Typography,
    Modal,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import ViewInArIcon from "@mui/icons-material/ViewInAr";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import axios from "axios";

const OblogorDocumentTable = ({ ObligorDocument, handleDeleteDoc }) => {
    const [rows, setRows] = React.useState([]);
    const [totalPages, setTotalPages] = React.useState(1);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);
    const [pdfOpen, setPdfOpen] = useState(false);
    const [pdfUrl, setPdfUrl] = useState(null);

    useEffect(() => {
        console.log("Obligor Document at Obligor table ", ObligorDocument);

        if (Array.isArray(ObligorDocument) && ObligorDocument.length > 0) {
            setRows(ObligorDocument);
            setTotalPages(Math.ceil(ObligorDocument.length / rowsPerPage));
        } else {
            setRows([]);
            setTotalPages(1);
        }
    }, [ObligorDocument, rowsPerPage]);

    const handleRowsPerPageChange = (event) => {
        const value = parseInt(event.target.value, 10);
        setRowsPerPage(value);
        setTotalPages(Math.ceil(rows.length / value));
        setCurrentPage(1);
    };

    const startIndex = (currentPage - 1) * rowsPerPage;
    const displayedRows = Array.isArray(rows)
        ? rows.slice(startIndex, startIndex + rowsPerPage)
        : [];

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

    const handlePdfOpen = (obligorDocId) => {
        fetchPdf(obligorDocId);
    };

    const handlePdfClose = () => {
        setPdfOpen(false);
        setPdfUrl(null);
    };
    const Token = localStorage.getItem('authToken');

    const fetchPdf = async (obligorDocId) => {
        try {
            const response = await axios.get(`http://localhost:9195/api/QueryObligor/View/${obligorDocId}`, {
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

    return (
        <Box sx={{ padding: 2, }}>
            <TableContainer component={Paper} sx={{ backgroundColor: "white", border: '1px solid #B2BEB5', height: "15vh" }}>
                <Table sx={{ minWidth: 300 }} aria-label="simple table">
                    <TableHead >
                        <TableRow >
                            <TableCell align="center"
                                sx={{
                                    fontWeight: "bold", border: "1px solid #B2BEB5",
                                    padding: "4px 8px", fontSize: '12px', width: '130px', color: "#0047AB"
                                }}
                            >
                                DOCUMENT NAME
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                UPLOADED ON
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: "12px", color: "#0047AB" }}
                            >
                                UPLOADED BY
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px', color: "#0047AB" }}
                            >
                                DELETE
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId}>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.documentName} <Button onClick={() => handlePdfOpen(row.obligorDocId)}><ViewInArIcon sx={{ blockSize: '20px', color: '#FF5E00', alignContent: 'center' }} /></Button>
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '11px' }}>
                                    {new Date(row.uploadedOn).toLocaleString('en-US', {
                                        weekday: 'long',   // "Monday"
                                        year: 'numeric',   // "2024"
                                        month: 'long',     // "November"
                                        day: 'numeric',    // "14"
                                        hour: '2-digit',   // "03"
                                        minute: '2-digit', // "29"
                                        second: '2-digit', // "35"
                                        hour12: true       // "AM/PM"
                                    })}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.uploadedBy}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    <DeleteIcon sx={{ blockSize: '20px', color: '#FF5E00' }} onClick={() => { handleDeleteDoc(row.obligorDocId) }} />
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>


            <div className="tablerow" style={{ display: 'flex', justifyContent: 'end', alignItems: 'center' }}>


                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        alignItems: "center",
                        marginBottom: 1,
                        paddingTop: 2
                    }}
                >
                    <Typography variant="body1" sx={{ marginRight: 1, color: '#0047AB', fontWeight: 'bold' }}>
                        selected rows
                    </Typography>
                    <Select
                        value={rowsPerPage}
                        onChange={handleRowsPerPageChange}
                        sx={{
                            width: 60,
                            height: 40,
                            backgroundColor: "transparent",
                            border: 'none', 
                            boxShadow : 'none',
                            '& .MuiOutlinedInput-root': {
                                border: 'none', 
                                '&:hover': {
                                    border: 'none',
                                },
                                '&.Mui-focused': {
                                    border: 'none', 
                                }
                            },
                            '& .MuiSelect-icon': {
                                display: 'none', 
                            }
                        }}
                    >
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((count) => (
                            <MenuItem
                                key={count}
                                value={count}
                                sx={{
                                    border: 'none', 
                                    padding: '5px 10px',
                                    '&:hover': {
                                        color: '#FF5E00',
                                        border: 'none',  
                                    }
                                }}
                            >
                                {count}
                            </MenuItem>
                        ))}
                    </Select>

                </Box>

                <Box
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        alignItems: "center",
                        marginTop: 2,
                    }}
                >
                    <div style={{display : 'flex'}}>
                    <Typography variant="body1" sx={{ marginX: 2 }}>
                        {currentPage} - {Math.min(currentPage * rowsPerPage, rows.length)} of{" "}
                        {rows.length}
                    </Typography>

                    <Button
                        variant="contained"
                        onClick={handlePreviousPage}
                        disabled={currentPage === 1}
                        sx={{
                            backgroundColor: "white",
                            color: "#FF5E00",
                            border: 'none',
                            boxShadow : 'none',
                            "&:hover": { backgroundColor: "white", 
                                border: 'none',
                                boxShadow : 'none'},
                            marginRight: 1,
                        }}
                        startIcon={<ArrowBackIosNewIcon />}
                    >

                    </Button>

                    <Button
                        variant="contained"
                        onClick={handleNextPage}
                        disabled={currentPage === totalPages}
                        sx={{
                            backgroundColor: 'white',
                            color: "#FF5E00",
                            border: 'none',
                            boxShadow : 'none',
                            "&:hover": { backgroundColor: "white",                            
                                border: 'none',
                                boxShadow : 'none'},
                            marginLeft: 1,
                        }}
                        startIcon={<ArrowForwardIosIcon sx={{ backgroundColor: 'transparent',  }} />}
                    >

                    </Button>
                    </div>

                    
                </Box>
            </div>
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
        </Box>
    );
};

export default OblogorDocumentTable;