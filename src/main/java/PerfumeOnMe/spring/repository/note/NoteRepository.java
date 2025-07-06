package PerfumeOnMe.spring.repository.note;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.Note;

public interface NoteRepository extends JpaRepository<Note, Long>, NoteRepositoryCustom {
	Optional<Note> findByName(String name);
}
