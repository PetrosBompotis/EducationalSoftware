package com.petrosb.EducationalSoftware.module;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "module")
public class Module {
    @Id
    @SequenceGenerator(
            name = "module_id_seq",
            sequenceName = "module_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "module_id_seq"
    )
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 15000)
    private String textContent;

    @Column(nullable = false, name = "description")
    private String description;

    @ElementCollection
    @CollectionTable(name = "module_image_urls")
    @Column(name = "url")
    private List<String> imageUrls;

    @Column(nullable = false)
    private String videoUrl;

    public Module() {
    }

    public Module(String title, String textContent, String description, List<String> imageUrls, String videoUrl) {
        this.title = title;
        this.textContent = textContent;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Module module = (Module) o;
        return Objects.equals(id, module.id) && Objects.equals(title, module.title) && Objects.equals(textContent, module.textContent) && Objects.equals(description, module.description) && Objects.equals(imageUrls, module.imageUrls) && Objects.equals(videoUrl, module.videoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, textContent, description, imageUrls, videoUrl);
    }

    @Override
    public String toString() {
        return "Module{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", textContent='" + textContent + '\'' +
                ", description='" + description + '\'' +
                ", imageUrls=" + imageUrls +
                ", videoUrl='" + videoUrl + '\'' +
                '}';
    }
}
