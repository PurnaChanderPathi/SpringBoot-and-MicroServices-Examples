import { Backdrop, Button, CircularProgress, Grid, TextField } from '@mui/material';
import axios from 'axios';
import React, { useState } from 'react';
import { properties } from './properties';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';
import NavbarLogin from './NavbarLogin';

const MailPage = () => {

    const navigate = useNavigate();
    const [mailDetails, setMailDetails] = useState({
        username: "",
        password: "",
        name: "",
        emails: "",
        appraisalType: "",
        appraisalScore: "",
        dueDate: "",
        subject: "",
        attachments: null,
    });

    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setMailDetails((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleFileChange = (e) => {
        setMailDetails((prevState) => ({
            ...prevState,
            attachments: e.target.files[0],
        }));
    };
    
    const mailApi = async () => {
        const token = localStorage.getItem("token");
        console.log(token);

        const formData = new FormData();
        Object.keys(mailDetails).forEach((key) => {
            formData.append(key, mailDetails[key]);
        });

        try {
            setLoading(true);
            const response = await axios.post(properties.sendMail, formData, {
                headers: { Authorization: `Bearer ${token}` },
            });
            console.log(response.data);
            setLoading(false);
            if (response.data.status === 200 && response.data.message === "Mail Sent Successfully") {
                console.log("Mail Sent Successfully");
                Swal.fire({
                    icon:'success',
                    title:response.data.message,
                    position:'center',
                    timer:'3000',
                    width:'850px !important',
                    showConfirmButton:false
                })
                emptyFeilds();
            } else {
                alert("Mail Not Sent");
            }
        } catch (error) {
            setLoading(false);
            console.error("Error sending mail:", error);
            alert("Mail Not Sent");
        }
    };

    const mailWOAApi = async () => {
        const token = localStorage.getItem("token");
        console.log(token);


        try {
            setLoading(true);
            const response = await axios.post(properties.sendMailWithoutAttachments, mailDetails, {
                headers: { Authorization: `Bearer ${token}` },
            });

            console.log(response.data);
            setLoading(false);
            if (response.data.status === 200 && response.data.message === "Mail Sent Successfully") {
                console.log("Mail Sent Successfully");
                Swal.fire({
                    icon:'success',
                    title:response.data.message,
                    position:'center',
                    timer:'3000',
                    width:'850px !important',
                    showConfirmButton:false
                })
                emptyFeilds();
            } else {
                alert("Mail Not Sent WOA");
            }
        } catch (error) {
            setLoading(false);
            console.error("Error sending mail:", error);
            alert("Mail Not Sent");
        }
    }

    const submit = async (e) => {
        e.preventDefault();
        if(mailDetails.attachments==null || ""){
            await mailWOAApi();
        }else{
            await mailApi();
        }
       console.log(mailDetails);
    }

    const emptyFeilds = () => {
        setMailDetails({
            username: "",
            password: "",
            name: "",
            emails: "",
            appraisalType: "",
            appraisalScore: "",
            dueDate: "",
            subject: "",
            attachments: null,
        })

    }
    return (
        <div>
            <NavbarLogin/>
            <form onSubmit={submit}>
                <Grid
                    container
                    display={'flex'}
                    flexDirection={'row'}
                    maxWidth={600}
                    alignItems={'center'}
                    justifyContent={'center'}
                    margin="auto"
                    marginTop={10}
                    padding={8}
                    borderRadius={5}
                    boxShadow={"5px 5px 10px #ccc"}
                    sx={{
                        ":hover": {
                            boxShadow: '10px 10px 20px #ccc',
                        },
                    }}
                >
                    <Grid item md={6} sm={12}>
                        <TextField
                            name='username'
                            value={mailDetails.username}
                            onChange={handleChange}
                            margin='normal'
                            type='text'
                            variant='outlined'
                            placeholder='Username'
                            required
                        />
                    </Grid>
                    <Grid item md={6} sm={12}>
                        <TextField
                            name='password'
                            value={mailDetails.password}
                            onChange={handleChange}
                            margin='normal'
                            type='password'
                            variant='outlined'
                            placeholder='Password'
                            required
                        />
                    </Grid>

                    <Grid item md={6} sm={12}>
                        <TextField
                            name='name'
                            value={mailDetails.name}
                            onChange={handleChange}
                            margin='normal'
                            type='text'
                            variant='outlined'
                            placeholder='Name'
                        />
                    </Grid>
                    <Grid item md={6} sm={12}>
                        <TextField
                            name='emails'
                            value={mailDetails.emails}
                            onChange={handleChange}
                            margin='normal'
                            type='email'
                            variant='outlined'
                            placeholder='Emails'
                            required
                        />
                    </Grid>

                    <Grid item md={6} sm={12}>
                        <TextField
                            name='appraisalType'
                            value={mailDetails.appraisalType}
                            onChange={handleChange}
                            margin='normal'
                            type='text'
                            variant='outlined'
                            placeholder='Appraisal Type'
                        />
                    </Grid>
                    <Grid item md={6} sm={12}>
                        <TextField
                            name='appraisalScore'
                            value={mailDetails.appraisalScore}
                            onChange={handleChange}
                            margin='normal'
                            type='text'
                            variant='outlined'
                            placeholder='Appraisal Score'
                        />
                    </Grid>

                    <Grid item md={6} sm={12}>
                        <TextField
                            name='dueDate'
                            value={mailDetails.dueDate}
                            onChange={handleChange}
                            margin='normal'
                            type='date'
                            variant='outlined'
                            placeholder='Due Date'
                        />
                    </Grid>
                    <Grid item md={6} sm={12}>
                        <TextField
                            name='subject'
                            value={mailDetails.subject}
                            onChange={handleChange}
                            margin='normal'
                            type='text'
                            variant='outlined'
                            placeholder='Subject'
                        />
                    </Grid>

                    <Grid item md={6} sm={12}>
                        <TextField
                            name='file'
                            onChange={handleFileChange}
                            margin='normal'
                            type='file'
                            variant='outlined'
                            placeholder='File'
                        />
                    </Grid>
                    <Grid item md={6} sm={12} paddingLeft={6}>
                        <Button type='submit' variant='contained'>Send Mail</Button>
                    </Grid>
                </Grid>
            </form>
            <Backdrop
                sx={{ color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1 }}
                open={loading}
            >
                <CircularProgress color="inherit" />
            </Backdrop>
        </div>
    );
};


export default MailPage;