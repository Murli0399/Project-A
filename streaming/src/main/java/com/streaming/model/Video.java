package com.streaming.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "videos")
@Data
public class Video {
    @Id
    private String id;
    private String title;
    private String filePath;
    private String format;
    private List<String> resolutions; // e.g., ["360p", "720p"]
    private List<String> segmentPaths; // Store available resolutions
    // Getters and setters
}
