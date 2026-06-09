package com.bway.springdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bway.springdemo.repository.FinanceRepository;
import com.bway.springdemo.utils.MailUtils;

@RestController
@RequestMapping("/api")
public class ContactController {

    @Autowired
    private MailUtils mailutil;

    @Autowired
    private FinanceRepository financeRepo;

    @PostMapping("/contact")
    public ResponseEntity<String> sendContactMessage(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String message) {

        mailutil.sendEmail(email, subject, message);

        return ResponseEntity.ok("Message Sent Successfully!");
    }

    @GetMapping("/news")
    public ResponseEntity<?> getNews() {

        return financeRepo.findTopByOrderByIdDesc()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}