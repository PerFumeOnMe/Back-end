package PerfumeOnMe.spring.repository.fragranceMiddleNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.FragranceMiddleNote;

public interface FragranceMiddleNoteRepository
	extends JpaRepository<FragranceMiddleNote, Long>, FragranceMiddleNoteRepositoryCustom {
}
