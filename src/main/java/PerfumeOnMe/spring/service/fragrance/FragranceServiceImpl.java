package PerfumeOnMe.spring.service.fragrance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.FragranceConverter;
import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.repository.fragrance.FragranceRepository;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FragranceServiceImpl implements FragranceService {

	private final FragranceRepository fragranceRepository;
	private final FragranceConverter fragranceConverter;

	@Override
	public FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId) {
		Fragrance fragrance = fragranceRepository.findByIdWithAllDetails(fragranceId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FRAGRANCE_NOT_FOUND));
		return fragranceConverter.toDetailDto(fragrance);
	}

}
