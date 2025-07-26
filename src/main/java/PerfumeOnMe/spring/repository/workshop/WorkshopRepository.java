package PerfumeOnMe.spring.repository.workshop;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

	List<Workshop> findAllByUser(User user);

	Optional<Workshop> findByIdAndUser(Long id, User user);
}
