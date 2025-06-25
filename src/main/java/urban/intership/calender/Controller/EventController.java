package urban.intership.calender.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urban.intership.calender.DTO.EventDTO;
import urban.intership.calender.Model.Event;
import urban.intership.calender.Request.CreateEventRequest;
import urban.intership.calender.Request.UpdateEventRequest;
import urban.intership.calender.Service.EventService;
import urban.intership.calender.DTO.EventDTO;

import java.util.List;

@RestController
@RequestMapping("/api/event")
public class EventController {
    @Autowired
    public EventService eventService;

    @GetMapping
    public List<EventDTO> getAllEventDTO(){
        return eventService.getAllEventDTO();
    }

    @GetMapping("/{email}")
    public List<EventDTO> getAllByEmail(@PathVariable String email){
        return eventService.getAllEventByEmail(email);
    }

    @PostMapping("/create")
    public EventDTO createEvent(@RequestBody CreateEventRequest request){
        return eventService.createEvent(request);
    }

    @PostMapping("/update/{id}")
    public EventDTO updateEvent(@PathVariable Long id, @RequestBody UpdateEventRequest request){
        return eventService.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
        eventService.delete(id);
            return ResponseEntity.ok().body("Event deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting event: " + e.getMessage());
        }
    }
}