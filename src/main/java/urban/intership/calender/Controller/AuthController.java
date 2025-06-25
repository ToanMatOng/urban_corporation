package urban.intership.calender.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urban.intership.calender.DTO.EmployeeRegisterDTO;
import urban.intership.calender.Model.Employee;
import urban.intership.calender.Request.LoginRequest;
import urban.intership.calender.Service.EmployeeService;
import urban.intership.calender.Utils.JWTUtils;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final EmployeeService employeeService;
    private final JWTUtils jwtUtils;

    @Autowired
    public AuthController(JWTUtils jwtUtils,EmployeeService employeeService) {
        this.jwtUtils = jwtUtils;
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        Employee registeredEmployee = employeeService.registerEmployee(employeeRegisterDTO);
        return new ResponseEntity<>(registeredEmployee, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = employeeService.login(loginRequest);
            String username = jwtUtils.extractUsername(token);
            List<String> roles = jwtUtils.extractRoles(token); // GỌI Ở ĐÂY ✅
            System.out.println("Roles in token: " + roles);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", username,
                    "roles", roles,
                    "message", "Đăng nhập thành công"
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
