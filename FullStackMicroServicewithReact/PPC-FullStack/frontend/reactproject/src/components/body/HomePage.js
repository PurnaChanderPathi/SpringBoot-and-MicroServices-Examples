import React from 'react';
import './HomePage.css';
import { Box, Button, Modal } from '@mui/material';
import CachedIcon from '@mui/icons-material/Cached';
import BasicTabs from './Tabs';
import PPCDetails from './PPCDetails';
import ReAssign from './ReAssign';
import Header from '../header/Header';

const HomePage = () => {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const [assignOpen,setAssignOpen] = React.useState(false);
  const handleOpenReAssign = () => setAssignOpen(true);
  const handleCloseAssign = () => setAssignOpen(false);

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
        <CachedIcon className="ReloadIcon" />
      </div>
      <div className="tabs">
        <BasicTabs />
      </div>

      {/* Wrap PPCDetails inside Box to support ref forwarding */}
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box className="modalBox">
          <PPCDetails handleClose={handleClose} />
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
    </div>
    
  );
};

export default HomePage;
