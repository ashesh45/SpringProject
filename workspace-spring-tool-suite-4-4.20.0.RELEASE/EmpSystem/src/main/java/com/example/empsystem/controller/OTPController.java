package com.example.empsystem.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.utils.MailUtils;

@RestController
@RequestMapping("/api/auth")
public class OTPController {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private MailUtils mailUtils;

    private final Map<String, Integer> otpStore = new ConcurrentHashMap<>();
    private final Map<String, Long> otpExpiry = new ConcurrentHashMap<>();

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String email) {

        Employee emp = empRepo.findByEmail(email);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not registered");
        }

        Random random = new Random();
        int otp = random.nextInt(900000) + 100000;

        otpStore.put(email, otp);
        otpExpiry.put(email, System.currentTimeMillis() + 5 * 60 * 1000);

        mailUtils.sendEmail(email, "Your OTP for Password Reset", "Your OTP is: " + otp);
        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String email,
                                       @RequestParam int otp) {

        Integer storedOtp = otpStore.get(email);
        Long expiry = otpExpiry.get(email);

        if (storedOtp == null || expiry == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No OTP found. Please request OTP again.");
        }

        if (System.currentTimeMillis() > expiry) {
            otpStore.remove(email);
            otpExpiry.remove(email);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP expired. Please request OTP again.");
        }

        if (storedOtp.equals(otp)) {
            otpStore.put(email, -1);
            return ResponseEntity.ok("OTP verified successfully");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestParam String email,
                                            @RequestParam String newPassword,
                                            @RequestParam String confirmPassword) {

        Integer verified = otpStore.get(email);
        if (verified == null || verified != -1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP not verified. Please verify OTP first.");
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match");
        }

        Employee emp = empRepo.findByEmail(email);
        if (emp == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email not found");
        }

        emp.setPassword(newPassword);
        empRepo.save(emp);

        otpStore.remove(email);
        otpExpiry.remove(email);

        return ResponseEntity.ok("Password changed successfully");
    }
}
