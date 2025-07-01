package PerfumeOnMe.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
}
