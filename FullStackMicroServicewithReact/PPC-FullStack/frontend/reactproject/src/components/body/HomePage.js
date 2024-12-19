import React, { useState } from 'react';
import './HomePage.css';
import { Box, Button, Modal, Tooltip, Typography } from '@mui/material';
import CachedIcon from '@mui/icons-material/Cached';
import BasicTabs from './Tabs';
import PPCDetails from './PPCDetails';
import ReAssign from './ReAssign';
import Header from '../header/Header';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import ReplayIcon from '@mui/icons-material/Replay';
import Swal from 'sweetalert2';

const HomePage = () => {
    const [open, setOpen] = React.useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => {
        setOpen(false);
    }
    const [assignOpen, setAssignOpen] = React.useState(false);
    const handleOpenReAssign = () => setAssignOpen(true);
    const handleCloseAssign = () => setAssignOpen(false);
    const handleReload = () => {
        window.location.reload();
    };
    const [buttonClicked,setButtonClicked] = useState(false);

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


    return (

        <div className="MainDiv">

            <div className="AdminScreen">
                <div>
                <Typography sx={{fontWeight: '300'}}>
                               <span style={{ textDecoration: 'underline',
                                textDecorationThickness: '4px', textDecorationColor: '#FF5E00',
                               textUnderlineOffset: '4px'  }} 
                               className='CasesunderLine'>Cas</span>es
                            </Typography>
                </div>
                <div className='CasesButtons'>
                    <div className='replayIconCls'>
                        <Button  onClick={handleReload}>
                            <Tooltip title='Reload'>
                            <ReplayIcon sx={{transform: 'scaleX(-1)', color: '#FF5E00'}} />
                            </Tooltip>
                        </Button>
                    </div>
                            <div className='CaseInitiate'>
                            <button className="AdminButton" onClick={handleOpen}>
                    Initiate New Review
                </button>   
                            </div>
                <button className="AdminButton">Admin Screen</button>
                </div>

            </div>
            <div className="NewReview">
                {/* <button className="AdminButton" onClick={handleOpen}>
                    Initiate New Review
                </button> */}
                {/* <button className="AdminButton" onClick={handleOpenReAssign}>Re-Assign</button> */}
            </div>
            {/* <div className="Reload">
                <button className='ReloadButtonHP' onClick={handleReload}>
                <CachedIcon className="ReloadIcon" />
                </button>
            </div> */}
            <div className="tabs">
                <BasicTabs buttonClicked={buttonClicked} setButtonClicked={setButtonClicked} />
            </div>

            <Modal
                open={open}
                onClose={handleClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box className="modalBox">
                    <PPCDetails handleClose={handleClose} showToast={showToast} />
                </Box>
            </Modal>
            <Modal
                open={assignOpen}
                onClose={handleCloseAssign}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
            >
                <Box className="modalBox">
                    <ReAssign handleCloseAssign={handleCloseAssign} />
                </Box>
            </Modal>
            <ToastContainer />
        </div>

    );
};

export default HomePage;
