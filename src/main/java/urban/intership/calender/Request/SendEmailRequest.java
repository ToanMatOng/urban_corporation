package urban.intership.calender.Request;

public record SendEmailRequest(
    String to,
    String subject,
    String text
) {} 