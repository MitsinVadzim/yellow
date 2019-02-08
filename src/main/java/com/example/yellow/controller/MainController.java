package com.example.yellow.controller;

import com.example.yellow.domain.Record;
import com.example.yellow.domain.User;
import com.example.yellow.repository.RecordRepository;
import com.example.yellow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class MainController {
    private final RecordRepository recordRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MainController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        Iterable<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Record record,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        record.setAuthor(user);
        if(bindingResult.hasErrors()){
            Map<String, String> collect = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(collect);
        }else {
            ControllerUtils.saveFile(record, file, uploadPath);
            recordRepository.save(record);
        }
        Iterable<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "main";
    }

}
