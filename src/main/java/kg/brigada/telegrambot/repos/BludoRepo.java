package kg.brigada.telegrambot.repos;

import kg.brigada.telegrambot.entities.Bludo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BludoRepo extends JpaRepository<Bludo, Long> {
}
