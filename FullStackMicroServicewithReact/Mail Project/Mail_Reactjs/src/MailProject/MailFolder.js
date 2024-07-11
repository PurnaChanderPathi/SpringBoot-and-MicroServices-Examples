import {AppBar, Box, Button, Grid, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField } from '@mui/material'
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { properties } from './properties'
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import { useNavigate } from 'react-router-dom';
import { toast } from 'react-toastify';

const MailFolder = () => {
  const navigate = useNavigate();
  const [userFolder, setUserFolder] = useState([]);
  const token = localStorage.getItem('token');

  const apiCallGetFolderDetails = async () => {
    try {
      const response = await axios.get(properties.getFolders, {
        headers: { Authorization: `Bearer ${token}` },
    });  
    setUserFolder(response.data.result)
    console.log("response",userFolder);   
    } catch (error) {
      console.log("Error Fetchinf Folder data", error);
    }
  };

  useEffect(() => {
    apiCallGetFolderDetails();
},[])

  const deleteFolderApi = async (folderName) => {
    try {
      const response = await axios.delete(properties.deleteFolder + `/${folderName}`, {
        headers: { Authorization: `Bearer ${token}`},
      });
      console.log("Delete Folder Response", response);
      setUserFolder(userFolder.filter(folder => folder.folderName !== folderName));  
      toast.success("Deleted Successfully",{
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      })    
    } catch (error) {
      console.log("Error deleting folder", error);
      toast.error("Error deleting folder",{
        position: "top-right",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "light",
      })
    }

  };

  const handleDelete = (folderName) => {
    deleteFolderApi(folderName);
  }

  return (
    <>
    <AppBar style={{height:'50px'}}>
      <toolbar>
        <Grid container sx={{ placeItems: "center" }}>
          <Grid item xs={10.8} />
          <Grid item xs={1.2} paddingTop={0.5}>
            <Button variant='contained' size='large' color='info' endIcon={<ArrowBackIcon/>} onClick={()=> {navigate('/sendMail')}}>back</Button>
          </Grid>
        </Grid>
      </toolbar>
    </AppBar>
     <Box display={'flex'} justifyContent={'center'} alignItems={'center'} marginTop={8}>
      <TableContainer component={Paper} sx={{ maxWidth: 600 }}>
        <Table aria-label="folder table">
          <TableHead>
            <TableRow>
              <TableCell>Folder ID</TableCell>
              <TableCell>Folder Name</TableCell>
              <TableCell align="right">Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {userFolder.map((folder, index) => (
              <TableRow key={index}>
                <TableCell>{folder.folderId}</TableCell>
                <TableCell>{folder.folderName}</TableCell>
                <TableCell align="right">
                  <Button variant="contained" color="primary" onClick={()=>{handleDelete(folder.folderName)}}>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
      
    </>
  )
}

export default MailFolder
