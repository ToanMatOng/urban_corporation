package urban.intership.calender.Service;

import org.springframework.stereotype.Service;
import urban.intership.calender.Request.ForgotPasswordRequest;

@Service
public interface EmailService {
    void sendVerificationCode(String email);
    void verifyCodeAndChangePassword(ForgotPasswordRequest request);
} 