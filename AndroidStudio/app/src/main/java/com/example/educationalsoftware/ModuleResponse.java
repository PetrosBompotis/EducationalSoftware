package com.example.educationalsoftware;

import java.util.List;

public class ModuleResponse {
    private Long id;
    private String title;
    private String textContent;
    private String extendedTextContent;
    private String description;
    private List<String> imageUrls;
    private String videoUrl;

    public ModuleResponse(Long id, String title, String textContent, String extendedTextContent, String description, List<String> imageUrls, String videoUrl) {
        this.id = id;
        this.title = title;
        this.textContent = textContent;
        this.extendedTextContent = extendedTextContent;
        this.description = description;
        this.imageUrls = imageUrls;
        this.videoUrl = videoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getTextContent() {
        return textContent;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getExtendedTextContent() {
        return extendedTextContent;
    }
}
