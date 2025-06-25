package urban.intership.calender.Request;

import java.time.LocalDate;

public record UpdateEventRequest(LocalDate day,
                                 Long workplace_id,
                                 Long workType_id,
                                 Long employee_id,
                                 int startTime,
                                 int endTime,
                                 String taskDescription,
                                 String status)  {
}
