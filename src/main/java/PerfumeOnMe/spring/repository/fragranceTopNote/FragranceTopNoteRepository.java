package PerfumeOnMe.spring.repository.fragranceTopNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.domain.mapping.FragranceTopNote;

public interface FragranceTopNoteRepository
	extends JpaRepository<FragranceTopNote, Long>, FragranceTopNoteRepositoryCustom {
}
