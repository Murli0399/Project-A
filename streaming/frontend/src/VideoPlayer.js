import React, { useEffect, useRef } from 'react';
import videojs from 'video.js';
import 'video.js/dist/video-js.css';

const VideoPlayer = ({ videoId }) => {
    const videoRef = useRef(null);
    const playerRef = useRef(null);

    useEffect(() => {
        if (!playerRef.current) {
            // Initialize Video.js player
            playerRef.current = videojs(videoRef.current, {
                controls: true,
                autoplay: false,
                preload: 'auto',
                sources: [
                    { src: `http://localhost:8080/api/videos/${videoId}_360p.mp4`, label: '360p', type: 'video/mp4' },
                    { src: `http://localhost:8080/api/videos/${videoId}_720p.mp4`, label: '720p', type: 'video/mp4' }
                ]
            });
            
            // Add resolution switcher if you installed it
            playerRef.current.updateSrc([
                { src: `http://localhost:8080/api/videos/${videoId}_360p.mp4`, label: '360p', type: 'video/mp4' },
                { src: `http://localhost:8080/api/videos/${videoId}_720p.mp4`, label: '720p', type: 'video/mp4' }
            ]);

            // Cleanup on unmount
            return () => {
                if (playerRef.current) {
                    playerRef.current.dispose();
                }
            };
        }
    }, [videoId]);

    return (
        <div data-vjs-player>
            <video ref={videoRef} className="video-js vjs-default-skin" />
        </div>
    );
};

export default VideoPlayer;
