package urban.intership.calender.Request;

public record ForgotPasswordRequest(String email,
                                    String token,
                                    String newPassword) {
} 