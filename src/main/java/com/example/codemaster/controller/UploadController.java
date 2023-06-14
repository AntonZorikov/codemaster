package com.example.codemaster.controller;

import com.example.codemaster.service.AuthorizationService;
import com.example.codemaster.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Controller
public class UploadController {

    @Autowired
    private AuthorizationService authorizationService;

    private final String uploadDirectory;
    private final String uploadDirectoryArchive;
    private final String uploadDirectoryIntroduction;
    private final String uploadDirectoryBanner;

    public UploadController(@Value("${upload.directory}") String uploadDirectory,
                            @Value("${upload_archive.directory}") String uploadDirectoryArchive,
                            @Value("${upload_introduction.directory}") String uploadDirectoryIntroduction,
                            @Value("${upload_banner.directory}") String uploadDirectoryBanner) {
        this.uploadDirectory = uploadDirectory;
        this.uploadDirectoryArchive = uploadDirectoryArchive;
        this.uploadDirectoryIntroduction = uploadDirectoryIntroduction;
        this.uploadDirectoryBanner = uploadDirectoryBanner;
    }

    @PostMapping("/uploadArchive")
    public String uploadArchive(@RequestParam("file") MultipartFile file, @RequestParam Long courseId, Model model,
                                HttpServletRequest request) {

        boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
        if(!userIsAuthorize){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "uploads";
        }

        if (file.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "not empty");
            return "uploads";
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        originalFilename = courseId + "." + extension;
        try {
            if(!originalFilename.substring(originalFilename.lastIndexOf(".") + 1).equals("rar")){
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Extension not rar");
                return "uploads";
            }
            Path filePath = Paths.get(uploadDirectoryArchive, originalFilename);
            file.transferTo(filePath.toFile());
            model.addAttribute("successfully", true);
            return "uploads";
        } catch (IOException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error");
            return "uploads";
        }
    }
    @PostMapping("/uploadIntroduction")
    public String uploadIntroduction(@RequestParam("file") MultipartFile file, @RequestParam Long courseId, Model model,
                                HttpServletRequest request) {
        boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
        if(!userIsAuthorize){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "uploads";
        }

        if (file.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "not empty");
            return "uploads";
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        originalFilename = courseId + "." + extension;
        try {
            if(!originalFilename.substring(originalFilename.lastIndexOf(".") + 1).equals("mp4")){
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Extension not mp4");
                return "uploads";
            }
            Path filePath = Paths.get(uploadDirectoryIntroduction, originalFilename);
            file.transferTo(filePath.toFile());
            model.addAttribute("successfully", true);
            return "uploads";
        } catch (IOException e) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "Error");
            return "uploads";
        }
    }
    @PostMapping("/uploadBanner")
    public String uploadBanner(@RequestParam("file") MultipartFile file, @RequestParam Long courseId, Model model,
                                     HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        Long userId = (Long) session.getAttribute("userId");
//
//        if (userId == null) {
//            model.addAttribute("error", true);
//            model.addAttribute("errorMessage", "User not authorized");
//            return "uploads";
//        }

        boolean userIsAuthorize = authorizationService.userIsAuthorize(request);
        if(!userIsAuthorize){
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "User not authorized");
            return "uploads";
        }


        if (file.isEmpty()) {
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", "not empty");
            return "uploads";
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        originalFilename = courseId + "." + extension;
        try {
            if(!originalFilename.substring(originalFilename.lastIndexOf(".") + 1).equals("png")){
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Extension not mp4");
                return "uploads";
            }

            BufferedImage image = ImageIO.read(file.getInputStream());

            int width = image.getWidth();
            int height = image.getHeight();

            if (width > 720 || height > 400) {
                model.addAttribute("error", true);
                model.addAttribute("errorMessage", "Image dimensions exceed the maximum limit");
                return "uploads";
            }

            Path filePath = Paths.get(uploadDirectoryBanner, originalFilename);
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

