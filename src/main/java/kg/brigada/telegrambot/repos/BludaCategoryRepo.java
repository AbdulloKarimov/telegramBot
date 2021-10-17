package kg.brigada.telegrambot.repos;

import kg.brigada.telegrambot.entities.BludaCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BludaCategoryRepo extends JpaRepository<BludaCategory, Long> {
}
