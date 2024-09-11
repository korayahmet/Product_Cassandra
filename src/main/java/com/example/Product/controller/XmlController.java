package com.example.Product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Product.service.DataService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/xml")
public class XmlController {

    private final DataService dataService;


    @GetMapping("/upload-form")
    public String showXmlUploadForm(Model model) {
        return "upload-xml";
    }

    @PostMapping("/upload")
    public String handleXmlUpload(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a file to upload.");
            return "upload-xml";
        }

        try {
            dataService.parseAndStoreXml(file.getInputStream());
            model.addAttribute("message", "File uploaded and data stored successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "Error uploading and storing data. Check the logs for details.");
        }

        return "upload-xml";
    }
}