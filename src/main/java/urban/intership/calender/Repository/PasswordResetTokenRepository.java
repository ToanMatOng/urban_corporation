package urban.intership.calender.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import urban.intership.calender.Model.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByEmailAndToken(String email, String token);
} 