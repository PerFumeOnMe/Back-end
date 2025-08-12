package PerfumeOnMe.spring.workshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.workshop.domain.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {

	List<Workshop> findAllByUser(User user);

	Optional<Workshop> findByIdAndUser(Long id, User user);

	boolean existsByUserAndSavedName(User user, String savedName);

	Optional<Workshop> findFirstByUserOrderByCreatedAtDesc(User user);
}
