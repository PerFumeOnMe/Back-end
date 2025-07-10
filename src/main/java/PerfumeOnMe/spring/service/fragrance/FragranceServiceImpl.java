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
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.mapping.UserFragrance;
import PerfumeOnMe.spring.repository.fragrance.FragranceRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.repository.userFragrance.UserFragranceRepository;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FragranceServiceImpl implements FragranceService {

	private final FragranceRepository fragranceRepository;
	private final UserRepository userRepository;
	private final UserFragranceRepository userFragranceRepository;

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
		Page<Fragrance> fragrancePage = fragranceRepository.findBySearchKeyword(keyword, pageable);

		List<FragranceResponseDTO.FragranceSearchResult> dtoList = FragranceConverter.toSearchResultDtoList(
			fragrancePage.getContent());

		Map<String, Object> result = new HashMap<>();
		result.put("fragranceList", dtoList);
		result.put("hasNext", fragrancePage.hasNext());

		return result;
	}

	// 향수 즐겨찾기 등록 API
	@Override
	@Transactional(readOnly = false)
	public FragranceResponseDTO.FavoriteResponseDTO addFavorite(Long userId, Long fragranceId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));
		Fragrance fragrance = fragranceRepository.findById(fragranceId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FRAGRANCE_NOT_FOUND));

		if (userFragranceRepository.existsByUserAndFragrance(user, fragrance)) {
			throw new GeneralException(ErrorStatus.ALREADY_FAVORITES_ERROR);
		}

		UserFragrance favorite = UserFragrance.builder()
			.user(user)
			.fragrance(fragrance)
			.build();

		userFragranceRepository.save(favorite);
		return FragranceConverter.toFavoriteResponseDTO(favorite);
	}

}
