package potato.server.town.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import potato.server.town.domain.Town;

import java.util.Optional;

public interface TownRepository extends JpaRepository<Town, Long> {
    Optional<Town> findByName(String name);
}
