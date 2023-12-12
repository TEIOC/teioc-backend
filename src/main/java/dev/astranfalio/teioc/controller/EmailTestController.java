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
    private static final String SENDER_MAIL = "noreply@teioc.com";
    private static final String SUCCES_RESPONSE = "Email sent successfully";

    @GetMapping("/send")
    @ResponseBody
    public String sendTestEmail() {
        String from = SENDER_MAIL;
        String to = "kumarasamy.yaathavan@hotmail.com";
        String subject = "Test Email";
        String body = "This is a test email from Spring Boot application.";
        emailService.sendSimpleMessage(from, to, subject, body);
        return SUCCES_RESPONSE;
    }

    @PostMapping("/reset-password")
    public String sendResetPasswordMail(@RequestBody InternEmailDto emailDto) {
        String from = SENDER_MAIL;
        String email = emailDto.getEmail();
        InternDto intern = internDataService.findInternByEmail(email);
        String to = email;
        String subject = "[TEIOC] Reset Password for your account!";
        String body = "Here is the link to reset your account's password : http://localhost:3000/reset-password/" + intern.getId() + " \n If you didn't ask for the change, report to administrator.";
        emailService.sendSimpleMessage(from,to,subject,body);
        return SUCCES_RESPONSE;
    }

    @PostMapping("/activate")
    public String sendActivationMail(@RequestBody InternEmailDto emailDto) {
        String from = SENDER_MAIL;
        String email = emailDto.getEmail();
        InternDto intern = internDataService.findInternByEmail(email);
        String to = email;
        String subject = "[TEIOC] Activate your account!";
        String body = "Here is the link to activate your account: http://localhost:3000/activate/" + intern.getId();
        emailService.sendSimpleMessage(from,to,subject,body);
        return SUCCES_RESPONSE;
    }
}
