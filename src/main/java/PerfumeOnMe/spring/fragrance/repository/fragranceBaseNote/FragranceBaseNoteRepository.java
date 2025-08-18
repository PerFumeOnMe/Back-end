package PerfumeOnMe.spring.fragrance.repository.fragranceBaseNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceBaseNote;

public interface FragranceBaseNoteRepository
	extends JpaRepository<FragranceBaseNote, Long>, FragranceBaseNoteRepositoryCustom {
}
