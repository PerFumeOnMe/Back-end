package PerfumeOnMe.spring.repository.fragranceBaseNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.FragranceBaseNote;

public interface FragranceBaseNoteRepository
	extends JpaRepository<FragranceBaseNote, Long>, FragranceBaseNoteRepositoryCustom {
}
