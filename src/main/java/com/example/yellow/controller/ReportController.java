package com.example.yellow.controller;

import com.example.yellow.domain.Record;
import com.example.yellow.domain.User;
import com.example.yellow.repository.UserRepository;
import com.example.yellow.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ReportController {

    private final UserRepository userRepository;
    private final ReportService reportService;

    @Autowired
    public ReportController(UserRepository userRepository, ReportService reportService) {
        this.userRepository = userRepository;
        this.reportService = reportService;
    }

    @GetMapping("/reports/{user}")
    public String userRecords(
            @PathVariable User user,
            Model model
    ) {
        List<Record> records = user.getRecords();
        if (records.size() != 0) {
            model.addAttribute("reports", reportService.getReports(records));
        }
        return "reportList";
    }
}
