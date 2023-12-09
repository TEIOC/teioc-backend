package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.dto.InternDto;
import dev.astranfalio.teioc.dto.InternEmailDto;
import dev.astranfalio.teioc.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import dev.astranfalio.teioc.service.InternDataService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailTestController {

    private EmailService emailService;

    private InternDataService internDataService;

    @GetMapping("/send")
    @ResponseBody
    public String sendTestEmail() {
        String from = "noreply@teioc.com";
        String to = "kumarasamy.yaathavan@hotmail.com";
        String subject = "Test Email";
        String body = "This is a test email from Spring Boot application.";
        emailService.sendSimpleMessage(from, to, subject, body);
        return "Email sent successfully";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody InternEmailDto emailDto) {
        String from = "noreply@teioc.com";
        String email = emailDto.getEmail();
        InternDto intern = internDataService.findInternByEmail(email);
        String to = email;
        String subject = "[TEIOC] Reset Password for your account!";
        String body = "Here is the link to reset your account's password : http://localhost:3000/reset-password/" + intern.getId() + " \n If you didn't ask for the change, report to administrator.";
        emailService.sendSimpleMessage(from,to,subject,body);
        return "Email sent successfully";
    }
}
