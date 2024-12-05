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
} from "@mui/material";
import React, { useEffect } from "react";
import DeleteIcon from "@mui/icons-material/Delete";
import ViewInArIcon from "@mui/icons-material/ViewInAr";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";

const OblogorDocumentTable = ({ObligorDocument}) => {
    const [rows, setRows] = React.useState([]); // Assuming rows are fetched and set here
    const [totalPages, setTotalPages] = React.useState(1);
    const [rowsPerPage, setRowsPerPage] = React.useState(5);
    const [currentPage, setCurrentPage] = React.useState(1);

    useEffect(() => {
        console.log("Obligor Document at Obligor table ",ObligorDocument);
        
        if(Array.isArray(ObligorDocument) && ObligorDocument.length > 0){
            setRows(ObligorDocument);
            setTotalPages(Math.ceil(ObligorDocument.length / rowsPerPage));
        }else{
            setRows([]);
            setTotalPages(1);
        }
    },[ObligorDocument,rowsPerPage]);

    // Handle rows per page selection
    const handleRowsPerPageChange = (event) => {
        const value = parseInt(event.target.value, 10);
        setRowsPerPage(value);
        setTotalPages(Math.ceil(rows.length / value)); 
        setCurrentPage(1);
    };

    // Pagination calculation
    const startIndex = (currentPage - 1) * rowsPerPage;
    const displayedRows = Array.isArray(rows)
        ? rows.slice(startIndex, startIndex + rowsPerPage)
        : [];

    // Handle next page
    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage((prev) => prev + 1);
        }
    };

    // Handle previous page
    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage((prev) => prev - 1);
        }
    };

    return (
        <Box sx={{ padding: 2 ,}}>
            {/* Rows per page dropdown */}

            {/* Table */}
            <TableContainer component={Paper} sx={{ backgroundColor: "white", border: '1px solid #B2BEB5', height: "15vh" }}>
                <Table sx={{ minWidth: 300 }} aria-label="simple table">
                    <TableHead >
                        <TableRow >
                            <TableCell align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5" , padding: "4px 8px", fontSize: '12px',}}
                            >
                                DOCUMENT NAME
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5" , padding: "4px 8px", fontSize: '12px'}}
                            >
                                UPLOADED ON
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5" , padding: "4px 8px",fontSize: "12px",}}
                            >
                                UPLOADED BY
                            </TableCell>
                            <TableCell
                                align="center"
                                sx={{ fontWeight: "bold", border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}
                            >
                                DELETE
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {displayedRows.map((row) => (
                            <TableRow key={row.reviewId}>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5", padding: "4px 8px", fontSize: '12px' }}>
                                    {row.documentName} <ViewInArIcon sx={{blockSize: '20px'}} />
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5",  padding: "4px 8px", fontSize: '12px' }}>
                                    {row.uploadedOn}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5",  padding: "4px 8px", fontSize: '12px' }}>
                                    {row.uploadedBy}
                                </TableCell>
                                <TableCell align="center" sx={{ border: "1px solid #B2BEB5",  padding: "4px 8px", fontSize: '12px' }}>
                                    <DeleteIcon sx={{blockSize: '20px'}} />
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
                </TableContainer>
            

            {/* Pagination controls */}
            <div className="tablerow" style={{display: 'flex', justifyContent: 'end', alignItems: 'center'}}>

           
            <Box
                    sx={{
                        display: "flex",
                        justifyContent: "flex-end",
                        alignItems: "center",
                        marginBottom: 1,
                        paddingTop: 2
                    }}
                >
                    <Typography variant="body1" sx={{ marginRight: 1 , color: '#0047AB', fontWeight: 'bold'}}>
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
                            '& .MuiOutlinedInput-root': {
                                '& fieldset': {
                                    border: 'none',
                                },
                                '&:hover fieldset': {
                                    border: 'none',
                                },
                                '&.Mui-focused fieldset': {
                                    border: 'none',
                                },
                            },
                        }}
                    >
                        {[5, 10, 15, 20].map((count) => (
                            <MenuItem key={count} value={count}
                            sx={{
                                border: 'none',  
                                padding: '5px 10px', 
                                '&:hover': {
                                  backgroundColor: '#FF5E00', 
                                },
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
    
                {/* Previous page arrow */}
                {/* Page range display */}
                <Typography variant="body1" sx={{ marginX: 2 }}>
                    {currentPage} - {Math.min(currentPage * rowsPerPage, rows.length)} of{" "}
                    {rows.length}
                </Typography>

                {/* Next page arrow */}
                <Button
                    variant="contained"
                    onClick={handlePreviousPage}
                    disabled={currentPage === 1}
                    sx={{
                        backgroundColor: "transparent",
                        border: 'none',
                        color: "#B2BEB5",
                        "&:hover": { backgroundColor: "transparent" },
                        marginRight: 1,
                    }}
                    startIcon={<ArrowBackIosNewIcon />}
                >
                    {/* No label here, just the arrow */}
                </Button>

                <Button
                    variant="contained"
                    onClick={handleNextPage}
                    disabled={currentPage === totalPages}
                    sx={{
                        color: "#B2BEB5",
                        "&:hover": { backgroundColor: "white" },
                        marginLeft: 1,
                        backgroundColor: 'red'
                    }}
                    startIcon={<ArrowForwardIosIcon sx={{backgroundColor: 'transparent'}} />}
                >
                    {/* No label here, just the arrow */}
                </Button>
            </Box>
            </div>
        </Box>
    );
};

export default OblogorDocumentTable;
