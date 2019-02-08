package com.example.yellow.controller;

import com.example.yellow.domain.Record;
import com.example.yellow.domain.User;
import com.example.yellow.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class UserRecordsController {

    private final RecordRepository recordRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserRecordsController(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @GetMapping("/user-records/{user}")
    public String userRecords(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User user,
            Model model,
            @RequestParam(required = false) Record record
    ) {
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
        if (record.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(distance)) {
                record.setDistance(distance);
            }
            if (!StringUtils.isEmpty(time)) {
                record.setTime(time);
            }
            if (!StringUtils.isEmpty(date)) {
                record.setDate(date);
            }
            ControllerUtils.saveFile(record, file, uploadPath);
            recordRepository.save(record);
        }
        return "redirect:/user-records/" + user;
    }
}
