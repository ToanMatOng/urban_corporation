package urban.intership.calender.Service.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import urban.intership.calender.Model.Employee;
import urban.intership.calender.Model.PasswordResetToken;
import urban.intership.calender.Repository.EmployeeRepository;
import urban.intership.calender.Repository.PasswordResetTokenRepository;
import urban.intership.calender.Request.ChangePasswordRequest;
import urban.intership.calender.Request.ForgotPasswordRequest;
import urban.intership.calender.Request.SendEmailRequest;
import urban.intership.calender.Service.EmailService;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordResetTokenRepository tokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void sendEmail(SendEmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.to());
        message.setSubject(emailRequest.subject());
        message.setText(emailRequest.text());
        mailSender.send(message);
    }

    @Override
    public void sendVerificationCode(String email) {
        String token = String.format("%06d", new Random().nextInt(999999));
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setEmail(email);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10));
        tokenRepository.save(resetToken);
        SendEmailRequest emailRequest = new SendEmailRequest(
                email,
                "Mã xác nhận đổi mật khẩu",
                "Mã xác nhận của bạn là: " + token + "\nMã sẽ hết hạn sau 10 phút."
        );
        sendEmail(emailRequest);
    }

    public boolean verifyCode(String email, String token) {
        PasswordResetToken resetToken = tokenRepository.findByEmailAndToken(email, token);
        if (resetToken == null) return false;
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) return false;
        return true;
    }

    @Override
    public void verifyCodeAndChangePassword(ForgotPasswordRequest request) {
        PasswordResetToken resetToken = tokenRepository.findByEmailAndToken(request.email(), request.token());
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã xác nhận không hợp lệ hoặc đã hết hạn.");
        }
        Employee employee = employeeRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));
        employee.setPassword(passwordEncoder.encode(request.newPassword()));
        employeeRepository.save(employee);
        // Xoá mã sau khi dùng
        tokenRepository.delete(resetToken);
        // Gửi email thông báo (tuỳ chọn)
        sendEmail(new SendEmailRequest(
                request.email(),
                "Đổi mật khẩu thành công",
                "Bạn đã đổi mật khẩu thành công. Nếu không phải bạn thực hiện, hãy liên hệ quản trị viên."
        ));
    }
} 