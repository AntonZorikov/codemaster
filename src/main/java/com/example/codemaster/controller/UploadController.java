package com.example.codemaster.controller;

import com.example.codemaster.model.CrUpCourseInputs;
import com.example.codemaster.model.UploadForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
public class UploadController {

    private final String uploadDirectory;

    public UploadController(@Value("${upload.directory}") String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long courseId, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "not empty");
            return "uploads";
        }
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        System.out.println(extension);
        originalFilename = courseId + "." + extension;
        System.out.println(originalFilename);
        try {
            if(!originalFilename.substring(originalFilename.lastIndexOf(".") + 1).equals("rar")){
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Extension not rar");
                return "uploads";
            }
            Path filePath = Paths.get(uploadDirectory, originalFilename);
            file.transferTo(filePath.toFile());
            model.addAttribute("successfully", true);
            return "uploads";
        } catch (IOException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error");
            return "uploads";
        }
    }
}

