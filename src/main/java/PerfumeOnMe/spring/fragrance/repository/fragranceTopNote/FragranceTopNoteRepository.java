package PerfumeOnMe.spring.fragrance.repository.fragranceTopNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceTopNote;

public interface FragranceTopNoteRepository
	extends JpaRepository<FragranceTopNote, Long>, FragranceTopNoteRepositoryCustom {
}
