import React, { useState } from 'react';
import { useDropzone } from 'react-dropzone';
import axios from 'axios';

const DragAndDropImage = () => {
  const [fileName, setFileName] = useState('');
  const [isUploading, setIsUploading] = useState(false);

  // Function to handle file drop
  const onDrop = (acceptedFiles) => {
    const file = acceptedFiles[0];
    if (file) {
      setFileName(file.name);  // Display the file name immediately after drop
      uploadImage(file);  // Call function to upload image
    }
  };

  const { getRootProps, getInputProps } = useDropzone({
    onDrop,
    accept: 'image/*',  // Allow only image files
  });

  // Upload image to backend
  const uploadImage = async (file) => {
    const formData = new FormData();
    formData.append('image', file);  // Append the file to form data

    try {
      setIsUploading(true);

      // Send POST request to backend with the image
      const response = await axios.post('http://localhost:8080/upload', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });

      if (response.data) {
        // Show the returned file name from backend
        setFileName(response.data);
        console.log('File uploaded successfully:', response.data);
      }
    } catch (error) {
      console.error('Error uploading image:', error);
    } finally {
      setIsUploading(false);
    }
  };

  return (
    <div>
      <div
        {...getRootProps()}
        style={{
          width: '300px',
          height: '300px',
          border: '2px dashed #aaa',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          flexDirection: 'column',
          textAlign: 'center',
          cursor: 'pointer',
        }}
      >
        <input {...getInputProps()} />
        <p>Drag and drop an image here</p>
      </div>

      {isUploading ? (
        <p>Uploading...</p>  // Display when uploading
      ) : (
        <p>File Name: {fileName}</p>  // Display file name after successful upload
      )}
    </div>
  );
};

export default DragAndDropImage;
