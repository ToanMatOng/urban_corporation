package urban.intership.calender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import urban.intership.calender.DTO.ProfileDTO;
import urban.intership.calender.Service.EmployeeService;

@RestController
@RequestMapping("api/profile")
public class ProfileController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{email}")
    public ProfileDTO getProfileByEmail(@PathVariable String email){
        return employeeService.getProfileByEmail(email);
    }
}
