import React, { useState } from 'react';
import './HomePage.css';
import { Box, Modal } from '@mui/material';
import CachedIcon from '@mui/icons-material/Cached';
import BasicTabs from './Tabs';
import PPCDetails from './PPCDetails';
import ReAssign from './ReAssign';
import Header from '../header/Header';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

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
        toast.success(message, {
            position: "top-center",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
        });
        setButtonClicked(true);
    };

    return (

        <div className="MainDiv">

            <div className="AdminScreen">
                <button className="AdminButton">Admin Screen</button>
            </div>
            <div className="NewReview">
                <button className="AdminButton" onClick={handleOpen}>
                    Initiate New Review
                </button>
                <button className="AdminButton" onClick={handleOpenReAssign}>Re-Assign</button>
            </div>
            <div className="Reload">
                <button className='ReloadButtonHP' onClick={handleReload}>
                <CachedIcon className="ReloadIcon" />
                </button>
            </div>
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
