package urban.intership.calender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urban.intership.calender.Request.ForgotPasswordRequest;
import urban.intership.calender.Service.EmailService;

@RestController
@RequestMapping("/api/mail")
public class Mailcontroller {
    @Autowired
    private EmailService emailService;

    @PostMapping("/verify/{email}")
    public ResponseEntity<?> sendVerificationCode(@PathVariable String email) {
       emailService.sendVerificationCode(email);
        return ResponseEntity.ok("Đã gửi mã xác nhận tới email");
    }

    @PostMapping("/verify-and-change-password")
    public ResponseEntity<?> verifyAndChangePassword(@RequestBody ForgotPasswordRequest request) {
        try {
            emailService.verifyCodeAndChangePassword(request);
            return ResponseEntity.ok("Đổi mật khẩu thành công.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
} 