package urban.intership.calender.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Long id;
    private String fullname;
    private String email;
    private String phone;
    private String position;
    private String address;
    private String startDate;
    private String avatar;
    private String roleName;
}
