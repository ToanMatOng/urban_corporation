package urban.intership.calender.Service;

import org.springframework.stereotype.Service;
import urban.intership.calender.Model.Headquarter;
import urban.intership.calender.Request.CreateHeadQuarterRequest;
import urban.intership.calender.DTO.HeadquarterDTO;

import java.util.List;

@Service
public interface HeadQuarterService {
    List<HeadquarterDTO> getAllHeadQuarter();
    Headquarter createHeadQuarter(CreateHeadQuarterRequest request);
    void delete(Long id);
}