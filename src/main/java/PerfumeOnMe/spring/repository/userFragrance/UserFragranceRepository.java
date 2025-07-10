package PerfumeOnMe.spring.repository.userFragrance;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.mapping.UserFragrance;

public interface UserFragranceRepository extends JpaRepository<UserFragrance, Long> {
	boolean existsByUserAndFragrance(User user, Fragrance fragrance);
}
