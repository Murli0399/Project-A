// src/App.js
import React, { useEffect, useState } from 'react';
import VideoUpload from './VideoUpload';
import VideoPlayer from './VideoPlayer';
import axios from 'axios';

const App = () => {
    const [videos, setVideos] = useState([]);
    const [selectedVideoId, setSelectedVideoId] = useState(null);

    const fetchVideos = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/videos');
            setVideos(response.data);
        } catch (error) {
            console.error('Error fetching videos:', error);
        }
    };

    useEffect(() => {
        fetchVideos();
    }, []);

    const handleVideoUpload = () => {
        fetchVideos(); // Refresh the video list after uploading
    };

    return (
        <div>
            <h1>Video Streaming Platform</h1>
            <VideoUpload onUpload={handleVideoUpload} />
            <h2>Available Videos</h2>
            <ul>
                {videos.map(video => (
                    <li key={video.id}>
                        {video.title} 
                        <button onClick={() => setSelectedVideoId(video.id)}>Watch</button>
                    </li>
                ))}
            </ul>
            {selectedVideoId && <VideoPlayer videoId={selectedVideoId} />}
        </div>
    );
};

export default App;
