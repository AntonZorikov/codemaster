package com.example.codemaster.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    private final String uploadDirectory;

    public UploadController(@Value("${upload.directory}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (file.isEmpty()) {

        }
        try {
            Path filePath = Paths.get(uploadDirectory, originalFilename);
            file.transferTo(filePath.toFile());



            return "uploads";
        } catch (IOException e) {
            return "error";
        }
    }
}

