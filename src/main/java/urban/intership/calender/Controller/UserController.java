package urban.intership.calender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urban.intership.calender.Model.Employee;
import urban.intership.calender.Repository.EmployeeRepository;
import urban.intership.calender.Request.ChangePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        Employee employee = employeeRepository.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));
        if (!passwordEncoder.matches(request.oldPassword(), employee.getPassword())) {
            return ResponseEntity.badRequest().body("Mật khẩu cũ không đúng!");
        }
        employee.setPassword(passwordEncoder.encode(request.newPassword()));
        employeeRepository.save(employee);
        return ResponseEntity.ok("Đổi mật khẩu thành công!");
    }
} 