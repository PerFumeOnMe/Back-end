package PerfumeOnMe.spring.fragrance.repository.fragranceMiddleNote;

import org.springframework.data.jpa.repository.JpaRepository;

import PerfumeOnMe.spring.fragrance.domain.mapping.FragranceMiddleNote;

public interface FragranceMiddleNoteRepository
	extends JpaRepository<FragranceMiddleNote, Long>, FragranceMiddleNoteRepositoryCustom {
}
