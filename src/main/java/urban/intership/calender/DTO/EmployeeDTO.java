package urban.intership.calender.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String roleName;
    private String address;
    private LocalDate startDate;
    private String avatar;
    private String position;
}
