package PerfumeOnMe.spring.repository.userNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.UserNote;

public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
}
