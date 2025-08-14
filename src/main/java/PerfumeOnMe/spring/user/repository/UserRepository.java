package PerfumeOnMe.spring.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

	Optional<User> findUserByLoginId(String loginId);

	Optional<User> findById(Long id);

	Optional<User> findUserByNickname(String nickname);
}
