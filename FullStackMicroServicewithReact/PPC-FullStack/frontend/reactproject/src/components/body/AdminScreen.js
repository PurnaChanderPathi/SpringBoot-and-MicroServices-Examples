import React, { useState } from "react";
import "./AdminScreen.css";
import {
  Box,
  Button,
  Chip,
  FormControl,
  MenuItem,
  Select,
  TextField,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import axios from "axios";
import Swal from "sweetalert2";

const AdminScreen = ({ handleCloseAdminScreen }) => {
  const [selectedRoles, setSelectedRoles] = useState([]);
  const [input, setInput] = useState({
    name: "",
    email: "",
    password: "",
    roles:""
  });
  const handleChange = (event) => {
    const value = event.target.value;
    const selectedValues = typeof value === "string" ? value.split(",") : value;

    setSelectedRoles(selectedValues);
  
    setInput((prev) => ({
      ...prev,
      roles: selectedValues.join(","),
    }));
  };

  const handleCancelButton = () => {
    setInput({
      name : '',
      email : '',
      password : '',
      roles : ''
    })
    setSelectedRoles([])
    handleCloseAdminScreen()
  }

  const exitButton = () => {
    handleCloseAdminScreen();
  };

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
  
    const showToastSuccess = (message) => {
      Swal.fire({
        icon: 'success',
        // title: 'Oops...',
        text: message,
        position: 'top-right',
        toast: true,
        timer: 5000,
        showConfirmButton: false,
        didClose: () => Swal.close(),
        customClass: {
          popup: 'swal-toast-popup',
        },
        background: 'Green',
        color: 'white',
      });
    };

    const handleCreateuserButton = () => {
      if(input.name !== "" && input.email !== "" && input.password !== "" && selectedRoles.length > 0){
        createUser();
        setInput({
          name : '',
          email : '',
          password : '',
          roles: ''
        })
        setSelectedRoles([])
      }else{
        showToast("Fill All the Fields");
      }


    }

  const createUser = async () => {
    const inputs = {
      name : input.name,
      email: input.email,
      password: input.password,
      roles : input.roles
    }
    console.log("inputs for User Creation : ",inputs);

    const urlForUserCreation = "http://localhost:1992/auth/createUser";

    try {
      const response = await axios.post(urlForUserCreation,inputs);

      if(response?.status === 201){
        console.log("User Created Successfully...!");
        console.log("message",response?.message);
        showToastSuccess("User Created")
        
      }
    } catch (error) {
      console.log("error creating User : ",error); 
          showToast("Failed To Create User");
    }

   
    
  }

  return (
    <div className="adminScreen">
      <div className="adminScreenMain">
        <div className="adminScreenHeading">
          <Typography className="typoAdminScreen" sx={{ fontWeight: "bold" }}>
            <span
              style={{
                textDecoration: "underline",
                textDecorationThickness: "4px",
                textDecorationColor: "#FF5E00",
                textUnderlineOffset: "4px",
              }}
              className="typoAdminScreen"
            >
              Cre
            </span>
            ate User
          </Typography>
          <button className="ExitIconButton" onClick={exitButton}>
            <CloseIcon className="ExitIcon" />
          </button>
        </div>
        <div className="adminScreenTextFields">
          <TextField
            required
            id="outlined-basic"
            label="Name"
            value={input.name}
            variant="outlined"
            onChange={(e) => setInput((prev) => ({ ...prev, name: e.target.value}))}
            className="adminTextFields"
            sx={{
              "& label": { color: "black" },
              "& .MuiInputLabel-root.Mui-focused": { color: "black" },
              "& .MuiOutlinedInput-root": {
                "& fieldset": { borderColor: "black" },
                "&:hover fieldset": { borderColor: "#FF5E00" },
                "&.Mui-focused fieldset": { borderColor: "#FF5E00" },
              },
            }}
          />

          <TextField
            required
            id="outlined-basic"
            label="Email"
            value={input.email}
            variant="outlined"
            onChange={(e) => setInput((prev) => ({...prev, email:e.target.value}))}
            className="adminTextFields"
            sx={{
              "& label": { color: "black" },
              "& .MuiInputLabel-root.Mui-focused": { color: "black" },
              "& .MuiOutlinedInput-root": {
                "& fieldset": { borderColor: "black" },
                "&:hover fieldset": { borderColor: "#FF5E00" },
                "&.Mui-focused fieldset": { borderColor: "#FF5E00" },
              },
            }}
          />
          <TextField
            required
            id="outlined-basic"
            label="Password"
            value={input.password}
            variant="outlined"
            onChange={(e) => setInput((prev) => ({...prev, password:e.target.value}))}
            className="adminTextFields"
            sx={{
              "& label": { color: "black" },
              "& .MuiInputLabel-root.Mui-focused": { color: "black" },
              "& .MuiOutlinedInput-root": {
                "& fieldset": { borderColor: "black" },
                "&:hover fieldset": { borderColor: "#FF5E00" },
                "&.Mui-focused fieldset": { borderColor: "#FF5E00" },
              },
            }}
          />
          <FormControl
            variant="outlined"
            className="adminTextFields"
            sx={{ minWidth: 200 }}
          >
            <Select
              required
              multiple
              value={selectedRoles}
              onChange={handleChange}
              displayEmpty
              renderValue={(selected) =>
                selected.length === 0 ? (
                  <span>Select Roles</span>
                ) : (
                  <Box sx={{ display: "flex", flexWrap: "wrap", gap: 0.5 }}>
                    {selected.map((role) => (
                      <Chip
                        key={role}
                        label={role}
                        sx={{ backgroundColor: "#FF5E00", color: "white" }}
                      />
                    ))}
                  </Box>
                )
              }
              sx={{
                "& .MuiOutlinedInput-notchedOutline": {
                  borderColor: "black",
                },
                "&:hover .MuiOutlinedInput-notchedOutline": {
                  borderColor: "#FF5E00",
                },
                "&.Mui-focused .MuiOutlinedInput-notchedOutline": {
                  borderColor: "#FF5E00 !important",
                },
                "& .MuiInputLabel-root.Mui-focused": { color: "black" },
              }}
            >
              <MenuItem value="SrCreditReviewer">SrCreditReviewer</MenuItem>
              <MenuItem value="HeadofPPC">HeadofPPC</MenuItem>
              <MenuItem value="CreditReviewer">CreditReviewer</MenuItem>
              <MenuItem value="SPOC">SPOC</MenuItem>
            </Select>

            <Box mt={2} sx={{ color: "black", fontWeight: "bold" }}>
              Selected Roles: {selectedRoles.join(", ")}
            </Box>
          </FormControl>
        </div>
        <div className="adminScreenButtons">
          <Button
            variant="contained" onClick={handleCreateuserButton}
            sx={{ backgroundColor: "#FF5E00", fontSize: "12px" }}
          >
            Create User
          </Button>
          <Button
            variant="contained" onClick={handleCancelButton}
            sx={{ backgroundColor: "#FF5E00", fontSize: "12px" }}
          >
            Cancel
          </Button>
        </div>
      </div>
    </div>
  );
};

export default AdminScreen;
