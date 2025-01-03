import { Box, Button, Modal, TextField, Typography } from '@mui/material';
import React, { useState } from 'react'
import CloseIcon from '@mui/icons-material/Close';
import EditOffIcon from '@mui/icons-material/EditOff';
import axios from 'axios';
import Swal from 'sweetalert2';
import { useDispatch } from 'react-redux';
import { getResponseRemediationDetailsByReviewId } from '../../redux/ResponseRemedaitionSlice';
import { useDropzone } from 'react-dropzone';
import OblogorDocumentTable from './OblogorDocumentTable';
import DriveFolderUploadIcon from '@mui/icons-material/DriveFolderUpload';

const ObligorAndResponseViewAndUpload = () => {

    const reviewId = localStorage.getItem('reviewId');
    const childReviewId = localStorage.getItem('childReviewId');
    const ApiToken = localStorage.getItem('authToken');
    const [ObligorDetails, setObligorDetails] = useState(null);
    const dispatch = useDispatch();
    const [fileName, setFileName] = useState('');
    const [file, setFile] = useState(null);
    const [isUploading, setIsUploading] = useState(false);
    const [uploadMessage, setUploadMessage] = useState('');
        const [ObligorDocument, setObligorDocument] = useState(null);
        


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
    const [input, setInput] = useState({
        Obligor: '',
        Division: '',
        Cifid: '',
        PremId: ''
    });

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

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 1100,
        bgcolor: 'background.paper',
        boxShadow: 24,
        height: 500,
    };

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

    return (
        <div>
            <Modal
                open={handleOpen}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box sx={style}>
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
            
        </div>
    )
}

export default ObligorAndResponseViewAndUpload
