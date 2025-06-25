package urban.intership.calender.Request;

public record ChangePasswordRequest(String email,
                                    String oldPassword,
                                    String newPassword) {
} 