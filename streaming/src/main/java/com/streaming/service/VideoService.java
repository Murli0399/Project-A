package com.streaming.service;


import com.streaming.model.Video;
import com.streaming.repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;


    public void uploadVideo(MultipartFile file) throws IOException, InterruptedException {
        String baseFileName = file.getOriginalFilename();
        String uploadDir = "D:/Project-A/streaming/Videos/";
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String inputFilePath = uploadDir + baseFileName;
        file.transferTo(new File(inputFilePath));

        // Define resolutions and segment output directory
        List<String> resolutions = Arrays.asList("360p", "720p");
        for (String resolution : resolutions) {
            String outputDir = uploadDir + resolution + "/";
            segmentVideo(inputFilePath, outputDir, resolution);
        }

        // Save metadata to the database
        Video video = new Video();
        video.setTitle(baseFileName);
        video.setFilePath(inputFilePath);
        video.setFormat("mp4");
        video.setResolutions(Collections.singletonList(resolutions));
        videoRepository.save(video);
    }

    private void segmentVideo(String inputFilePath, String outputDirPath, String resolution) throws IOException, InterruptedException {
        File outputDir = new File(outputDirPath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        String[] command;
        switch (resolution) {
            case "360p":
                command = new String[]{
                        "ffmpeg",
                        "-i", inputFilePath,
                        "-vf", "scale=640:360",
                        "-c:a", "copy",
                        "-f", "segment",
                        "-segment_time", "10",
                        "-reset_timestamps", "1",
                        outputDirPath + "output_%03d_360p.mp4"
                };
                break;
            case "720p":
                command = new String[]{
                        "ffmpeg",
                        "-i", inputFilePath,
                        "-vf", "scale=1280:720",
                        "-c:a", "copy",
                        "-f", "segment",
                        "-segment_time", "10",
                        "-reset_timestamps", "1",
                        outputDirPath + "output_%03d_720p.mp4"
                };
                break;
            default:
                throw new IllegalArgumentException("Unsupported resolution: " + resolution);
        }

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg process failed with exit code " + exitCode);
        }
    }


    private void runFFmpegCommand(String inputFilePath, String outputFilePath, int width, int height) throws IOException, InterruptedException {
        String ffmpegPath = "C:\\ffmpeg\\bin\\ffmpeg";  // Replace with the absolute path to ffmpeg.exe

        String[] command = {
                ffmpegPath,
                "-i", inputFilePath,
                "-vf", "scale=" + width + ":" + height,
                outputFilePath
        };

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        // Capture FFmpeg output for debugging
        try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Wait for the process to complete
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg process failed with exit code " + exitCode);
        }
    }




    // Method to stream video
    public FileSystemResource streamVideo(String id) {
        // Retrieve the video from the database
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found"));

        // Build the file path from the stored file path
        Path videoPath = Paths.get(video.getFilePath());

        // Check if the file exists
        File file = videoPath.toFile();
        if (!file.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video file not found");
        }

        // Return the file as a Resource
        return new FileSystemResource(file);
    }
}
