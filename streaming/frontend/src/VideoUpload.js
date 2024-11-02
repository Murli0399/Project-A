// src/VideoUpload.js
import React, { useState } from 'react';
import axios from 'axios';

const VideoUpload = ({ onUpload }) => {
    const [file, setFile] = useState(null);

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);
        try {
            await axios.post('http://localhost:8080/api/videos/upload', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });
            onUpload(); // Callback to update the video list
            setFile(null);
        } catch (error) {
            console.error('Error uploading video:', error);
        }
    };

    return (
        <div>
            <h2>Upload Video</h2>
            <input type="file" accept="video/*" onChange={handleFileChange} />
            <button onClick={handleUpload}>Upload</button>
        </div>
    );
};

export default VideoUpload;
