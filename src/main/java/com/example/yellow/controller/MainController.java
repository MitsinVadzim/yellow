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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MainController {
    private final RecordRepository recordRepository;
    private final UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public MainController(RecordRepository recordRepository, UserRepository userRepository) {
        this.recordRepository = recordRepository;
        this.userRepository = userRepository;
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
//        if(record.getDistance() != null && !user.getPassword().equals(passwordConfirm)){
//            model.addAttribute("passwordError", "Password are different!");
//        }
        if(bindingResult.hasErrors()){
            Map<String, String> collect = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(collect);
        }else {
            saveFile(record, file);
            recordRepository.save(record);
        }
        Iterable<Record> records = recordRepository.findAll();
        model.addAttribute("records", records);
        return "main";
    }

    private void saveFile(@Valid Record record, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFileName));
            record.setFilename(resultFileName);
        }
    }

    @GetMapping("/user-records/{user}")
    public String userRecords(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Record record
    ){
        List<Record> records = user.getRecords();
        model.addAttribute("records", records);
        model.addAttribute("tempRecord", record);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "userRecords";
    }

    @PostMapping("/user-records/{user}")
    public String updateRecord(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Record record,
            @RequestParam("distance") Integer distance,
            @RequestParam("time") Double time,
            @RequestParam("date") String date,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if(record.getAuthor().equals(currentUser)){
            if(!StringUtils.isEmpty(distance)){
                record.setDistance(distance);
            }
            if(!StringUtils.isEmpty(time)){
                record.setTime(time);
            }
            if(!StringUtils.isEmpty(date)){
                record.setDate(date);
            }
            saveFile(record,file);
            recordRepository.save(record);
        }
        return "redirect:/user-records/" + user;
    }
}
