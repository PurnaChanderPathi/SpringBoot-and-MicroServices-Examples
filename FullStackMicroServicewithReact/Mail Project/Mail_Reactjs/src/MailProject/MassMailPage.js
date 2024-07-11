import React, { useEffect, useState } from 'react';
import NavbarLogin from './NavbarLogin';
import { Box, Button, FormControl, Grid, InputLabel, MenuItem, Select, TextField, Typography } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import axios from 'axios';
import { properties } from './properties';
import { toast } from 'react-toastify';

const MassMailPage = () => {
    const [inputs, setInputs] = useState({
        username: "",
        cc: "",
        password: "",
        file: null,
        zipFile: null,
        subject: "",
        body: "",
    });

    const [usernames, setUsernames] = useState([]);
    const [credentials, setCredentials] = useState([]);

    useEffect(() => {
        // Fetch credentials when component mounts
        fetchCredentials();
    }, []);

    const fetchCredentials = async () => {
        const token = localStorage.getItem("token");
        if (!token) {
            alert("No token found, please log in.");
            return;
        }

        try {
            const response = await axios.get(properties.getCredentails, {
                headers: { Authorization: `Bearer ${token}` },
            });

            if (response.data.result && response.data.result.length > 0) {
                const creds = response.data.result;
                setCredentials(creds); // Save the full credentials data
                setUsernames(creds.map(item => item.mailId));
            } else {
                console.error("No credentials found in response.");
                alert("No credentials found.");
            }
        } catch (error) {
            if (error.response && error.response.status === 403) {
                alert("Access forbidden: Please check your permissions.");
            } else {
                console.error("Error getting credentials:", error);
                alert("Error in get call credentials");
            }
        }
    };

    const handleUsernameChange = (e) => {
        const selectedMailId = e.target.value;
        const selectedCredential = credentials.find(cred => cred.mailId === selectedMailId);

        setInputs(prevInputs => ({
            ...prevInputs,
            username: selectedCredential ? selectedCredential.mailId : "",
            password: selectedCredential ? selectedCredential.password : "",
        }));
    };

    const handleFileChange = (e, fieldName) => {
        const file = e.target.files[0];
        setInputs(prevInputs => ({
            ...prevInputs,
            [fieldName]: file,
        }));
    };

    const massMailApi = async () => {
        const token = localStorage.getItem("token");

        const formData = new FormData();
        Object.keys(inputs).forEach((key) => {
            formData.append(key, inputs[key]);
        });

        try {
            const response = await axios.post(properties.sendMassMail, formData, {
                headers: { 'Content-Type': 'multipart/form-data', Authorization: `Bearer ${token}` },
            });
            console.log("response", response);
            toast.success("Mail Sent Successfully!", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            setInputs({
                username: "",
                cc: "",
                password: "",
                file: null,
                zipFile: null,
                subject: "",
                body: "",
            })
        } catch (error) {
            console.error("Error sending mail:", error);
            toast.error("Error sending mail", {
                position: "top-right",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        }
    };

    return (
        <div>
            <NavbarLogin />
            <Box
                sx={{
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    justifyContent: 'center',
                    height: '100vh',
                    backgroundColor: '#f4f6f8',
                    padding: 4,
                    margin: 2
                }}
            >
                <Box
                    sx={{
                        width: 500,
                        backgroundColor: 'white',
                        padding: 4,
                        borderRadius: 2,
                        boxShadow: 3,
                    }}
                >
                    <Typography variant="h4" component="h1" gutterBottom>
                        Send Mass Email
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <FormControl variant='outlined' fullWidth>
                                <InputLabel id='username'>Username</InputLabel>
                                <Select
                                    labelId="username-label"
                                    id="username"
                                    value={inputs.username}
                                    onChange={handleUsernameChange}
                                    label="Username"
                                >
                                    {usernames.map((mailId, index) => (
                                        <MenuItem key={index} value={mailId}>{mailId}</MenuItem>
                                    ))}
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="CC"
                                variant="outlined"
                                fullWidth
                                type='text'
                                value={inputs.cc}
                                onChange={(e) => setInputs(prevInputs => ({
                                    ...prevInputs,
                                    cc: e.target.value,
                                }))}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="subject"
                                variant="outlined"
                                fullWidth
                                rows={4}
                                value={inputs.subject}
                                onChange={(e) => setInputs(prevInputs => ({
                                    ...prevInputs,
                                    subject: e.target.value,
                                }))}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                label="body"
                                variant="outlined"
                                fullWidth
                                multiline
                                rows={4}
                                value={inputs.body}
                                onChange={(e) => setInputs(prevInputs => ({
                                    ...prevInputs,
                                    body: e.target.value,
                                }))}
                            />
                        </Grid>
                        <Grid item xs={12} container spacing={2}>
                            <Grid item xs={6}>
                                <input
                                    type="file"
                                    onChange={(e) => handleFileChange(e, 'file')}
                                />
                            </Grid>
                            <Grid item xs={6}>
                                <input
                                    type="file"
                                    onChange={(e) => handleFileChange(e, 'zipFile')}
                                />
                            </Grid>
                        </Grid>
                        <Grid item xs={12}>
                            <Button
                                variant="contained"
                                color="primary"
                                endIcon={<SendIcon />}
                                onClick={massMailApi}
                            >
                                Send
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        </div>
    );
};

export default MassMailPage;
