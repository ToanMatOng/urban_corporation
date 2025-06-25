package urban.intership.calender.Service;

import org.springframework.stereotype.Service;
import urban.intership.calender.DTO.EventDTO;
import urban.intership.calender.Model.Event;
import urban.intership.calender.Request.CreateEventRequest;
import urban.intership.calender.Request.UpdateEventRequest;

import java.util.List;

@Service
public interface EventService {
    List<Event> getAllEvent();
    List<EventDTO> getAllEventDTO();
    EventDTO createEvent(CreateEventRequest request);
    EventDTO updateEvent(Long id, UpdateEventRequest request);
    void delete(Long id);
    List<EventDTO> getAllEventByEmail(String email);
}