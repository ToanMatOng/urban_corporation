package urban.intership.calender.Controller;

import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import urban.intership.calender.DTO.EmployeeDTO;
import urban.intership.calender.DTO.EmployeeRegisterDTO;
import urban.intership.calender.DTO.ProfileDTO;
import urban.intership.calender.Model.Employee;
import urban.intership.calender.Request.LoginRequest;
import urban.intership.calender.Service.EmployeeService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    // Endpoint để lấy tất cả nhân viên
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Endpoint để lấy thông tin nhân viên theo ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Endpoint để xóa nhân viên
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint để cập nhật thông tin nhân viên
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeRegisterDTO employeeRegisterDTO) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeRegisterDTO);
        if (updatedEmployee != null) {
            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}