package PerfumeOnMe.spring.service.fragrance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

	// 향수 상세 API
	@Override
	public FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId) {
		Fragrance fragrance = fragranceRepository.findByIdWithAllDetails(fragranceId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FRAGRANCE_NOT_FOUND));
		return FragranceConverter.toDetailDto(fragrance);
	}

	// 향수 검색 API
	@Override
	public Map<String, Object> searchFragrances(String keyword, int page, int size) {
		PageRequest pageable = PageRequest.of(page, size);
		Page<Fragrance> fragrancePage = fragranceRepository.findByKeyword(keyword, pageable);

		List<FragranceResponseDTO.FragranceSearchResult> dtoList = FragranceConverter.toSearchResultDtoList(
			fragrancePage.getContent());

		Map<String, Object> result = new HashMap<>();
		result.put("fragranceList", dtoList);
		result.put("hasNext", fragrancePage.hasNext());

		return result;
	}

}
