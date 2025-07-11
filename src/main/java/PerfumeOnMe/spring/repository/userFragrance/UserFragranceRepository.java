package PerfumeOnMe.spring.repository.userFragrance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.mapping.UserFragrance;

public interface UserFragranceRepository extends JpaRepository<UserFragrance, Long>, UserFragranceRepositoryCustom {
	boolean existsByUserAndFragrance(User user, Fragrance fragrance);

	Optional<UserFragrance> findByUserAndFragrance(User user, Fragrance fragrance);

	boolean existsByUserIdAndFragranceId(Long userId, Long fragranceId);

}
