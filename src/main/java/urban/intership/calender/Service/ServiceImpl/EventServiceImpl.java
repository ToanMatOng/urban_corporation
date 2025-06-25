package urban.intership.calender.Service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import urban.intership.calender.DTO.EventDTO;
import urban.intership.calender.DTO.HeadquarterDTO;
import urban.intership.calender.DTO.WorkTypeDTO;
import urban.intership.calender.DTO.EmployeeDTO;
import urban.intership.calender.Model.Employee;

import urban.intership.calender.Model.Event;
import urban.intership.calender.Model.Headquarter;
import urban.intership.calender.Model.Worktype;
import urban.intership.calender.Repository.EmployeeRepository;
import urban.intership.calender.Repository.EventRepository;
import urban.intership.calender.Repository.HeadQuarterRepository;
import urban.intership.calender.Repository.WorkTypeRepository;
import urban.intership.calender.Request.CreateEventRequest;
import urban.intership.calender.Request.UpdateEventRequest;
import urban.intership.calender.Service.EventService;
import java.util.Collections;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private WorkTypeRepository workTypeRepository;
    @Autowired
    private HeadQuarterRepository headQuarterRepository;

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public List<EventDTO> getAllEventDTO() {
        List<Event> events = eventRepository.findAll();
        return events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private boolean checkEventIfExistReturnFalse(CreateEventRequest request){
        //find all event in this day and time
        Event checkEventTime=eventRepository.findEventsInTimeRange(request.day(),request.startTime(),request.endTime(), request.employee_id());
        if (checkEventTime!= null)return false;
        return true;
    }


    @Override
    public EventDTO createEvent(CreateEventRequest request) {
        if (request.employee_id() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null.");
        }
        if (request.workType_id() == null) {
            throw new IllegalArgumentException("Work type ID cannot be null.");
        }
        if (request.workplace_id() == null) {
            throw new IllegalArgumentException("Workplace ID cannot be null.");
        }

        //check employee, work type, work place if not exist return error
        Employee employee = employeeRepository.findById(request.employee_id())
                .orElseThrow(() -> new RuntimeException("User is not exist"));
        Worktype workType = workTypeRepository.findById(request.workType_id())
                .orElseThrow(() -> new RuntimeException("Work type is not exist"));
        Headquarter headquarter = headQuarterRepository.findById(request.workplace_id())
                .orElseThrow(() -> new RuntimeException("Work place is not exist"));

        Event newEvent = new Event();
        newEvent.setDay(request.day());
        newEvent.setStartTime(request.startTime());
        newEvent.setEndTime(request.endTime());
        newEvent.setTaskDescription(request.taskDescription());
        newEvent.setStatus(request.status());
        newEvent.setEmployee(employee);
        newEvent.setWorkplace(headquarter);
        newEvent.setWorkType(workType);

        Event savedEvent = eventRepository.save(newEvent);
        return convertToDto(savedEvent);
    }

    @Override
    public EventDTO updateEvent(Long id, UpdateEventRequest request) {
        Event eventToUpdate = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        Headquarter headquarter = headQuarterRepository.findById(request.workplace_id())
                .orElseThrow(() -> new RuntimeException("Headquarter not found with id: " + request.workplace_id()));

        Worktype worktype = workTypeRepository.findById(request.workType_id())
                .orElseThrow(() -> new RuntimeException("Worktype not found with id: " + request.workType_id()));

        Employee employee = employeeRepository.findById(request.employee_id())
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + request.employee_id()));

        eventToUpdate.setDay(request.day());
        eventToUpdate.setWorkplace(headquarter);
        eventToUpdate.setWorkType(worktype);
        eventToUpdate.setEmployee(employee);
        eventToUpdate.setStartTime(request.startTime());
        eventToUpdate.setEndTime(request.endTime());
        eventToUpdate.setStatus(request.status());
        eventToUpdate.setTaskDescription(request.taskDescription());

        Event updatedEvent = eventRepository.save(eventToUpdate);
        return convertToDto(updatedEvent);
    }

    private EventDTO convertToDto(Event event) {
        EventDTO dto = new EventDTO();
        dto.setId(event.getId());

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(event.getEmployee().getId());
        employeeDTO.setFullname(event.getEmployee().getFullname());
        employeeDTO.setEmail(event.getEmployee().getEmail());
        dto.setEmployee(employeeDTO.getId());

        HeadquarterDTO workplaceDto = new HeadquarterDTO();
        workplaceDto.setId(event.getWorkplace().getId());
        workplaceDto.setName(event.getWorkplace().getName());
        workplaceDto.setAddress(event.getWorkplace().getAddress());
        workplaceDto.setDescription(event.getWorkplace().getDescription());
        dto.setWorkplace(workplaceDto);

        WorkTypeDTO workTypeDto = new WorkTypeDTO();
        workTypeDto.setId(event.getWorkType().getId());
        workTypeDto.setWorkMode(event.getWorkType().getWorkMode());
        workTypeDto.setDescription(event.getWorkType().getDescription());
        dto.setWorkType(workTypeDto);

        dto.setDay(event.getDay());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setTaskDescription(event.getTaskDescription());
        dto.setStatus(event.getStatus());
        return dto;
    }

    public List<EventDTO> getAllEventByEmail(String email) {
        Optional<Employee> employeeOpt = employeeRepository.findByEmail(email);
        if (employeeOpt.isEmpty()) {
            return Collections.emptyList(); // hoặc throw exception nếu muốn
        }

        Long employeeId = employeeOpt.get().getId();
        List<Event> events = eventRepository.findByEmployeeId(employeeId);

        return events.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }



    @Override
    public void delete(Long id) {
        // Kiểm tra xem event có tồn tại không trước khi xóa
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with id: " + id);
        }
        eventRepository.deleteById(id);
    }
}