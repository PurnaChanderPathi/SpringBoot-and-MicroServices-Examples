import { Accordion, AccordionDetails, AccordionSummary, Alert, Box, Button, Modal, outlinedInputClasses, Tab, Tabs, TextareaAutosize, TextField, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react'
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import NoteAddIcon from '@mui/icons-material/NoteAdd';
import './FieldWorkStage.css'
import EditOffIcon from '@mui/icons-material/EditOff';
import CloseIcon from '@mui/icons-material/Close';
import { useDropzone } from 'react-dropzone';
import { Margin } from '@mui/icons-material';
import DriveFolderUploadIcon from '@mui/icons-material/DriveFolderUpload';
import axios from 'axios';
import Obligortable from './Obligortable';
import OblogorDocumentTable from './OblogorDocumentTable';
import { toast, ToastContainer } from 'react-toastify';
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';

function CustomTabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
}


// Accessibility props for Tabs
function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

const FieldWorkStage = () => {

    const [value, setValue] = useState(0);
    const [open, setOpen] = React.useState(false);
    const [openObligor, setOpenObligor] = React.useState(false);
    const [openObservation,setOpenObservation] = useState(false);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
    const handleOpenObligor = () => setOpenObligor(true);
    const handleCloseObligor = () => setOpenObligor(false);
    const handleOpenObservation = () => setOpenObservation(true);
    const handleCloseObservation = () => setOpenObservation(false);
    const [input, setInput] = useState( {
        Obligor : '',
        Division : '',
        Cifid: '',
        PremId: ''
    });

    //     const [rows, setRows] = React.useState([]);
    // const [totalPages, setTotalPages] = React.useState(1);
    // const [rowsPerPage, setRowsPerPage] = React.useState(5);

    const [fileName, setFileName] = useState('');
    const [file,setFile] = useState(null);
    const [isUploading, setIsUploading] = useState(false);
    const [uploadMessage, setUploadMessage] = useState('');
  

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

    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 1100,
        bgcolor: 'background.paper',
        boxShadow: 24,
        height: 450,
    };

    const styleObligor = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 900,
        bgcolor: 'background.paper',
        boxShadow: 24,
        height: 330,
    };
    const ApiToken = localStorage.getItem("authToken");
    const reviewId = localStorage.getItem("reviewId");
    const createdBy = localStorage.getItem('username');
    const [ObligorDetails, setObligorDetails] = useState(null);
    const [ObligorDocument, setObligorDocument] = useState(null);
 
    
    useEffect(() => {
        if(reviewId){
            getObligorDetailsByReviewId();
            getObligorDocumentByReviewId();

        }
    },[reviewId]);

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
                },500)             
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
    const childReviewId = localStorage.getItem('childReviewId');

    const updateObligorWithChildId = async () => {

        if(input.Obligor !== null || input.Division !== null || input.Cifid !== null || input.PremId !== null){
            const inputs = {
                reviewId : reviewId,
                obligorName : input.Obligor,
                division : input.Division,
                obligorCifId : input.Cifid,
                obligorPremId : input.PremId,
                childReviewId : childReviewId
            }
            console.log("inputs in updateObligorWithChildId",input);
    
            let url = "http://localhost:9195/api/ActionObligor/updateObligorByChildReviewId";
    
            try {
                const response = await axios.put(url,inputs, {
                    headers : {
                        'Authorization': `Bearer ${ApiToken}`,
                        'Content-Type': 'application/json',
                    }
                });
    
                if(response.data.status === 200){
                    console.log("updated ObligorDetails successfully...!");
                    console.log("result on update Obligor",response.data.result);                
                    setInput({
                        Obligor: '',
                        Division: '',
                        Cifid: '',
                        PremId: '',
                    });
                    getObligorDetailsByReviewId();  
                    handleClose();         
                }else if(response.data.status === 404){
                    console.log(" Failed to updated ObligorDetails");
                    setInput({
                        Obligor: '',
                        Division: '',
                        Cifid: '',
                        PremId: '',
                    });
                }
            } catch (error) {
                console.log("Error while the processing update",error.message);
                setInput({
                    Obligor: '',
                    Division: '',
                    Cifid: '',
                    PremId: '',
                });      
            }
        }else {
            showToast("Please Fill Details to Update");
        }
        
           
    }

    const handleObservationObligor = () => {

        handleCloseObservation();
    }
 

    const handleobligor = async () => {

        const inputs = {
            reviewId : reviewId,
            obligorName : input.Obligor,
            division : input.Division,
            obligorCifId : input.Cifid,
            obligorPremId : input.PremId,
            createdBy : createdBy,
            reviewStatus : 'in-Progress'

        }
        console.log("inputs for FWS",inputs);
        console.log("reviewId at FWS",reviewId);

        if(input.Obligor !== "" && input.Division !== "" && input.Cifid !== "" && input.PremId !== "")
            {
            
                let  url = "http://localhost:9195/api/ActionObligor/save";
                console.log("token at FWS",ApiToken);
                
                try {
          
                  const response = await axios.post(url, inputs, {
                      headers : {
                          'Authorization': `Bearer ${ApiToken}`,
                          'Content-Type': 'application/json',
                      }
                  });
                  
                  if(response.data.status === 200 ){
                      console.log("FWS inserted Successfully...!",response.data.message)
                      setInput({
                          Obligor: '',
                          Division: '',
                          Cifid: '',
                          PremId: '',
                      });
                      getObligorDetailsByReviewId();   
                      handleCloseObligor();      
                  }else{
                      console.log("Failed to insert FWS Details");     
                      setInput({
                          Obligor: '',
                          Division: '',
                          Cifid: '',
                          PremId: '',
                      });   
                      handleClose();      
                  }        
                } catch (error) {
                  console.log("Error while processing to insert FWS Details",error.message);  
                  setInput({
                      Obligor: '',
                      Division: '',
                      Cifid: '',
                      PremId: '',
                  });
                  handleCloseObligor();   
                }
        }else {
            showToast("Fill All the Above Details");
        }
        

    }

    const handleDeleteDoc = (obligorId) => {

        handleObligorDocDelete(obligorId);
        setTimeout(() => {
            getObligorDocumentByReviewId();
        },500)
    }

    const handleObligorDocDelete = async (obligorId) => {

        let url = `http://localhost:9195/api/ActionObligor/deleteDoc/${obligorId}`;

        try {
            const response = await axios.delete(url, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });
            if(response.data.status === 200){
                console.log("obligorDoc Deleted Successfully...!");           
            }else {
                console.log("File Deletion Failed");
                
            }
        } catch (error) {
            console.log("Error deleting file :",error.message);
            
        }
    }

    const handleDelete = (obligorId) => {

        handleObligorDelete(obligorId);
        setTimeout(() => {
            getObligorDetailsByReviewId();
        },500)
    }

    const handleObligorDelete = async (obligorId) => {

        let url = `http://localhost:9195/api/ActionObligor/delete/${obligorId}`;

        try {
            const response = await axios.delete(url, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });
            if(response.data.status === 200){
                console.log("obligor Deleted Successfully...!");           
            }else {
                console.log("File Deletion Failed");
                
            }
        } catch (error) {
            console.log("Error deleting file :",error.message);
            
        }
    }

    const getObligorDetailsWithChildReviewId = async (childReviewId) => {

        const url = `http://localhost:9195/api/QueryObligor/findByChildReviewId/${childReviewId}`;

        try {
            const response = await axios.get(url, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });

            if(response.data.status === 200){
                const result = response.data.result;
                console.log("obligor findByChildReviewId :",result);                
                setInput({
                    Obligor: result.obligorName || '',
                    Division: result.division || '',
                    Cifid: result.obligorCifId || '',
                    PremId: result.obligorPremId || '',
                });
            }else if(response.data.status === 404){
                setInput({
                    Obligor: '',
                    Division: '',
                    Cifid: '',
                    PremId: '',
                });
            }else{
                console.log("data not found with reviewId :",reviewId);
                setInput({
                    Obligor: '',
                    Division: '',
                    Cifid: '',
                    PremId: '',
                });
            }
                       
        } catch (error) {
            console.log("Error fetching Obligor Document with reviewId :",reviewId);
            setInput({
                Obligor: '',
                Division: '',
                Cifid: '',
                PremId: '',
            });
            
        }
        
    }

    const getObligorDocumentByReviewId = async () => {

        const reviewId = localStorage.getItem("reviewId");
        console.log("reviewId at Obligor get function",reviewId);


        const url = `http://localhost:9195/api/QueryObligor/getObligorDocumentByReviewId?reviewId=${reviewId}`;

        try {
            const response = await axios.get(url, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });

            if(response.data.status === 200){
                setObligorDocument(response.data.result);
                console.log("getObligorDocumentByReviewId",ObligorDocument);
            }else if(response.data.status === 404){
                setObligorDocument(null);              
            }else{
                console.log("data not found with reviewId :",reviewId);
            }
                       
        } catch (error) {
            console.log("Error fetching Obligor Document with reviewId :",reviewId);
            
        }
        
    }

    const getObligorDetailsByReviewId = async () => {

        const reviewId = localStorage.getItem("reviewId");
        console.log("reviewId at Obligor get function",reviewId);


        const url = `http://localhost:9195/api/QueryObligor/getObligorDetailsByReviewId?reviewId=${reviewId}`;

        try {
            const response = await axios.get(url, {
                headers : {
                    'Authorization': `Bearer ${ApiToken}`,
                    'Content-Type': 'application/json',
                }
            });

            if(response.data.status === 200){
                setObligorDetails(response.data.result);
                console.log("getObligorDetailsByReviewId",ObligorDetails);
            }else if(response.data.status === 404){
                setObligorDetails(null);              
            }else{
                console.log("data not found with reviewId :",reviewId);
            }
                       
        } catch (error) {
            console.log("Error fetching Obligor Details with reviewId :",reviewId);
            
        }
        
    }

    return (
        <div className="PlanningStage">
                  <ToastContainer />
            <Accordion className="PlanningStageDD" sx={{ boxShadow: 'none', border: 'none' }}>
                <AccordionSummary
                    sx={{
                        backgroundColor: 'transparent',
                        color: 'black',
                        padding: '10px',
                        height: '20px',
                        fontSize: '15px',
                        border: '1px solid #B2BEB5',
                        borderRadius: '5px',
                        '& .MuiAccordionSummary-content': {
                            backgroundColor: 'transparent',
                            borderRadius: '4px',
                            padding: '5px 10px',
                        },
                    }}
                    expandIcon={<ExpandMoreIcon style={{ color: '#FF5E00' }} />}
                    aria-controls="panel1-content"
                    id="panel1-header"
                >
                    <Typography
                        sx={{ fontWeight: 'bold' }}>
                        <span style={{
                            textDecoration: 'underline',
                            textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                            textUnderlineOffset: '4px'
                        }}
                            className='underlineText'>Fie</span>ld Work Stage
                    </Typography>
                </AccordionSummary>
                <AccordionDetails>
                    <Box sx={{ width: '100%' }}>
                        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                            <Tabs value={value} onChange={handleChange} aria-label="basic tabs example" indicatorColor="none">
                                <Tab
                                    // label="Field Work Stage"
                                    {...a11yProps(0)}
                                    sx={{
                                        bgcolor: value === 0 ? 'transparent' : 'transparent',
                                        color: value === 0 ? 'black' : 'black',
                                        borderTopLeftRadius: 5,
                                        borderTopRightRadius: 5,
                                        marginLeft: 1.5,
                                        textTransform: 'none',
                                        position: 'relative',
                                        padding: '4px 12px',
                                        fontSize: '14px',
                                        '&:hover': {
                                            bgcolor: value === 0 ? 'transparent' : 'transparent',
                                            color: value === 0 ? 'black' : 'black',
                                        },
                                        '&.Mui-selected': {
                                            bgcolor: 'transparent',
                                            color: 'black',
                                        },
                                    }}
                                />
                            </Tabs>
                        </Box>
                        <CustomTabPanel value={value} index={0} className="CommentsScreenPS">
                            <div className='fieldworkMD'>
                                <div className='ObligorPage'>
                                    <div>
                                        <Button className='obButton' variant='contained' startIcon={<NoteAddIcon />}
                                            onClick={handleOpenObligor} >
                                            ADD NEW OBLIGOR
                                        </Button>
                                        <Modal
                                            open={openObligor}
                                            onClose={handleCloseObligor}
                                            aria-labelledby="modal-modal-title"
                                            aria-describedby="modal-modal-description"
                                        >
                                            <Box sx={styleObligor}>
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
                                                        <Button onClick={handleCloseObligor}><CloseIcon /></Button>
                                                    </div>
                                                    <div className='ObligorIP'>
                                                            <div className='obligorsetone'>
                                                            <TextField className='ObligorsetoneTF'
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
                                                             onChange={(e) => setInput({...input, Obligor: e.target.value})}
                                                             />

                                                            <TextField className='ObligorsetoneTF' 
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
                                                            onChange={(e) => setInput({...input, Division: e.target.value})}
                                                            />
                                                            </div>
                                                            <div className='obligorsettwo'>
                                                            <TextField className='obligorsettwoTF'
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
                                                             onChange={(e) => setInput({...input, Cifid: e.target.value})}
                                                             />

                                                            <TextField className='obligorsettwoTF'
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
                                                             onChange={(e) => setInput({...input, PremId: e.target.value})} />
                                                            </div>
                                                            <div className='obligorSubmitBtnAdd'>
                                                            <Button 
                                                            endIcon={<ArrowUpwardIcon />} variant='contained' sx={{ backgroundColor: '#FF5E00' }}
                                                            onClick={handleobligor}
                                                            >Submit</Button>
                                                            </div>

                                                    </div>

                                                </div>


                                            </Box>
                                        </Modal>
                                        <Modal
                                            open={open}
                                            onClose={handleClose}
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
                                                             onChange={(e) => setInput({...input, Obligor: e.target.value})}
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
                                                            onChange={(e) => setInput({...input, Division: e.target.value})}
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
                                                             onChange={(e) => setInput({...input, Cifid: e.target.value})}
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
                                                             onChange={(e) => setInput({...input, PremId: e.target.value})} />
                                                             
                                                            <Button 
                                                            startIcon={<EditOffIcon />} variant='contained' sx={{ backgroundColor: '#093414' }}
                                                            onClick={updateObligorWithChildId}
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
                                                                    sx={{backgroundColor : '#FF5E00', width: '100px', height: '35px', fontSize: '12px'}} 
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
                                        <Modal
                                            open={openObservation}
                                            onClose={handleCloseObservation}
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
                                                                className='underlineText'>Obs</span>ervations
                                                        </Typography>
                                                        <Button onClick={handleCloseObservation}><CloseIcon /></Button>
                                                    </div>
                                                    <div className='FieldWorkObligor'>
                                                        <div className='FieldWorkStageIP'>
                                                            
                                                            <TextareaAutosize minRows={21} style={{ border : '1px solid #FF5E00',borderRadius: '5px',
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
                                                            }} />
                                                             
                                                            <Button
                                                            startIcon={<EditOffIcon />} variant='contained' sx={{ backgroundColor: '#093414' }}
                                                            onClick={handleObservationObligor}
                                                            >ADD</Button>
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
                                                                    sx={{backgroundColor : '#FF5E00', width: '100px', height: '35px', fontSize: '12px'}} 
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

                                </div>
                                <div className='FWSTable'>
                                    <Obligortable ObligorDetails={ObligorDetails}
                                     handleDelete={handleDelete}
                                      handleOpen={handleOpen}
                                      getObligorDetailsWithChildReviewId={getObligorDetailsWithChildReviewId}
                                      handleOpenObservation={handleOpenObservation}
                                      />
                                </div>
                            </div>
                        </CustomTabPanel>
                    </Box>
                </AccordionDetails>
            </Accordion>
            <div className='planningPS'>
                {/* <Label>Hello </Label> */}
            </div>
        </div>
    );
}

export default FieldWorkStage
