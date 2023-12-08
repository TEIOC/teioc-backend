package dev.astranfalio.teioc.controller;

import dev.astranfalio.teioc.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@AllArgsConstructor
public class EmailTestController {

    private EmailService emailService;

    @GetMapping("/send")
    @ResponseBody
    public String sendTestEmail() {
        String to = "test@example.com";
        String subject = "Test Email";
        String body = "This is a test email from Spring Boot application.";
        emailService.sendSimpleMessage(to, subject, body);
        return "Email sent successfully";
    }
}
