package urban.intership.calender.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urban.intership.calender.DTO.HeadquarterDTO;
import urban.intership.calender.Model.Event;
import urban.intership.calender.Model.Headquarter;
import urban.intership.calender.Repository.EventRepository;
import urban.intership.calender.Repository.HeadQuarterRepository;
import urban.intership.calender.Request.CreateHeadQuarterRequest;
import urban.intership.calender.Service.HeadQuarterService;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class HeadQuartServiceImpl implements HeadQuarterService {
    @Autowired
    private HeadQuarterRepository headQuarterRepository;
    @Autowired
    private EventRepository eventRepository;

    private HeadquarterDTO toHeadquarterDTO(Headquarter headquarter){
        HeadquarterDTO dto = new HeadquarterDTO();
        dto.setId(headquarter.getId());
        dto.setName(headquarter.getName());
        dto.setAddress(headquarter.getAddress());
        dto.setDescription(headquarter.getDescription());
        return dto;
    }

    @Override
    public List<HeadquarterDTO> getAllHeadQuarter() {
        return headQuarterRepository.findAll()
                .stream()
                .map(this::toHeadquarterDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Headquarter createHeadQuarter(CreateHeadQuarterRequest request) {
        Headquarter newHeadQuarter= new Headquarter();
        newHeadQuarter.setName(request.name());
        newHeadQuarter.setAddress(request.address());
        newHeadQuarter.setDescription(request.description());

        return headQuarterRepository.save(newHeadQuarter);
    }

    @Override
    public void delete(Long id) {
        headQuarterRepository.deleteById(id);
    }
}