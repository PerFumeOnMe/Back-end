package PerfumeOnMe.spring.user.repository.userNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.user.domain.mapping.UserNote;

public interface UserNoteRepository extends JpaRepository<UserNote, Long> {

	void deleteAllByUser(User user);
}
