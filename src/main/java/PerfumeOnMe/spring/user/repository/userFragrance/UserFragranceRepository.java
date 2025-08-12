package PerfumeOnMe.spring.user.repository.userFragrance;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.Fragrance;
import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.user.domain.mapping.UserFragrance;

public interface UserFragranceRepository extends JpaRepository<UserFragrance, Long>, UserFragranceRepositoryCustom {
	boolean existsByUserAndFragrance(User user, Fragrance fragrance);

	Optional<UserFragrance> findByUserAndFragrance(User user, Fragrance fragrance);

	boolean existsByUserIdAndFragranceId(Long userId, Long fragranceId);

	Page<UserFragrance> findAllByUserId(Long userId, Pageable pageable);

}
