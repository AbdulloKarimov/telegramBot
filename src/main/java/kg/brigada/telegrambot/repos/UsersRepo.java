package kg.brigada.telegrambot.repos;

import kg.brigada.telegrambot.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
    Users getUsersByUserId(String userId);
}
