package com.streaming.controller;

import com.streaming.model.Video;
import com.streaming.repository.VideoRepository;
import com.streaming.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/videos")
@CrossOrigin(origins = "*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @Autowired
    private VideoRepository videoRepository;

    @GetMapping("/helth")
    public ResponseEntity<String> helthCheck(){
        return ResponseEntity.ok("Good");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(@RequestParam("file") MultipartFile file) {
        videoService.uploadVideo(file);
        return ResponseEntity.ok("Video uploaded successfully");
    }

    @GetMapping("/stream/{id}")
    public ResponseEntity<FileSystemResource> streamVideo(@PathVariable String id) {
        FileSystemResource videoResource = videoService.streamVideo(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + videoResource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("video/mp4"))
                .body(videoResource);
    }

    @GetMapping
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
