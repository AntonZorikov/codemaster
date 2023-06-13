package com.example.codemaster.model;
import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
    private Long courseId;
    private MultipartFile file;

    public UploadForm() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
