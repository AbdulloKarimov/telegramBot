package kg.brigada.telegrambot.repos;

import kg.brigada.telegrambot.entities.BludaCategory;
import kg.brigada.telegrambot.entities.Bludo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BludoRepo extends JpaRepository<Bludo, Long> {
    List<Bludo> findAllByBludaCategory(BludaCategory bludaCategory);
}
