package urban.intership.calender.Service.ServiceImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // Dùng interface Spring Security
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import urban.intership.calender.Model.Employee;
import urban.intership.calender.Repository.EmployeeRepository;

import java.util.List;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority(employee.getRole().getName()) // ví dụ: "ROLE_ADMIN"
        );
        // Trả về UserDetails, ví dụ dùng class User có sẵn của Spring Security
        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                authorities
//                List.of(() -> employee.getRole().getName())  // Chuyển role thành GrantedAuthority
        );


    }
}
