package com.example.restservice.imageUpload;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
    @GetMapping("/imageUpload")
    public String showForm(Model model) {
        return "imageUpload/imageUpload_form";
    }

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
    
    @GetMapping("/uploadimage")
    public String displayUploadForm(Model model) {
        return "imageupload/index";
    }

    @PostMapping("/upload")
    public String uploadImage(Model model, @RequestParam("image") MultipartFile file) throws IOException {
        StringBuilder fileNames = new StringBuilder();
        
        System.out.println(UPLOAD_DIRECTORY);
        
        Path fileNamePath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
        fileNames.append(file.getOriginalFilename());
        Files.write(fileNamePath, file.getBytes());
        model.addAttribute("msg", "Uploaded images: " + fileNames.toString());
        //return "imageupload/index";
        return "index";
    }
}
