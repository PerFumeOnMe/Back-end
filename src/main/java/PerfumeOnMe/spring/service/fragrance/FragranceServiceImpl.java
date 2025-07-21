package PerfumeOnMe.spring.service.fragrance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.converter.FragranceConverter;
import PerfumeOnMe.spring.domain.Fragrance;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.enums.FragranceGender;
import PerfumeOnMe.spring.domain.enums.FragranceType;
import PerfumeOnMe.spring.domain.mapping.UserFragrance;
import PerfumeOnMe.spring.repository.fragrance.FragranceRepository;
import PerfumeOnMe.spring.repository.location.LocationRepository;
import PerfumeOnMe.spring.repository.note.NoteRepository;
import PerfumeOnMe.spring.repository.season.SeasonRepository;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.repository.userFragrance.UserFragranceRepository;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceRequestDTO;
import PerfumeOnMe.spring.web.dto.fragrance.FragranceResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FragranceServiceImpl implements FragranceService {

	private final FragranceRepository fragranceRepository;
	private final UserRepository userRepository;
	private final UserFragranceRepository userFragranceRepository;
	private final NoteRepository noteRepository;
	private final SeasonRepository seasonRepository;
	private final LocationRepository locationRepository;

	// 향수 상세 API
	@Override
	public FragranceResponseDTO.FragranceDetailResult getFragranceDetail(Long fragranceId, Long userId) {
		Fragrance fragrance = fragranceRepository.findByIdWithAllDetails(fragranceId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FRAGRANCE_NOT_FOUND));

		boolean liked = (userId != null) && Like(userId, fragranceId);

		return FragranceConverter.toDetailDto(fragrance, liked);
	}

	// 향수 검색 API
	@Override
	public FragranceResponseDTO.FragranceSearchFinalResult searchFragrances(
		FragranceRequestDTO.FragranceSearchRequest request, Long userId) {
		PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
		Page<Fragrance> fragrancePage = fragranceRepository.findBySearchKeyword(request.getKeyword(), pageable);

		return getFragranceSearchFinalResult(userId, fragrancePage);

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

	// 향수 즐겨찾기 취소 API
	@Override
	@Transactional(readOnly = false)
	public FragranceResponseDTO.FavoriteCancelResponseDTO deleteFavorite(Long userId, Long fragranceId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));
		Fragrance fragrance = fragranceRepository.findById(fragranceId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FRAGRANCE_NOT_FOUND));

		UserFragrance favorite = userFragranceRepository.findByUserAndFragrance(user, fragrance)
			.orElseThrow(() -> new GeneralException(ErrorStatus.FAVORITE_NOT_FOUND));

		userFragranceRepository.delete(favorite);
		return FragranceConverter.toFavoriteCancelResponseDTO(favorite);
	}

	// 향수 필터링 API
	@Override
	public FragranceResponseDTO.FragranceSearchFinalResult searchFragrancesByFilter(
		FragranceRequestDTO.FragranceFilterRequest request, Long userId) {

		//  Enum 유효성 검사
		if (request.getGender() != null) {
			try {
				FragranceGender.valueOf(request.getGender());
			} catch (IllegalArgumentException e) {
				throw new GeneralException(ErrorStatus.INVALID_GENDER);
			}
		}

		if (request.getFragranceType() != null) {
			try {
				FragranceType.valueOf(request.getFragranceType());
			} catch (IllegalArgumentException e) {
				throw new GeneralException(ErrorStatus.INVALID_FRAGRANCE_TYPE);
			}
		}

		// 가격 범위 유효성 검사
		if (request.getPriceMin() != null && request.getPriceMax() != null
			&& request.getPriceMin() > request.getPriceMax()) {
			throw new GeneralException(ErrorStatus.INVALID_PRICE_RANGE);
		}

		// ID 존재 유효성 검사 (note, season, situation)
		if (request.getNoteCategoryId() != null && !noteRepository.existsById(request.getNoteCategoryId())) {
			throw new GeneralException(ErrorStatus.INVALID_NOTE_ID);
		}
		if (request.getSeasonId() != null && !seasonRepository.existsById(request.getSeasonId())) {
			throw new GeneralException(ErrorStatus.INVALID_SEASON_ID);
		}
		if (request.getSituationId() != null && !locationRepository.existsById(request.getSituationId())) {
			throw new GeneralException(ErrorStatus.INVALID_SITUATION_ID);
		}

		Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), Sort.by("name"));
		Page<Fragrance> fragrancePage = fragranceRepository.findByFilter(request, pageable);

		return getFragranceSearchFinalResult(userId, fragrancePage);
	}

	// 향수 전체 리스트 API
	@Override
	public FragranceResponseDTO.FragranceSearchFinalResult getFragranceListAll(
		FragranceRequestDTO.FragranceAllRequest request, Long userId) {
		PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
		Page<Fragrance> fragrancePage = fragranceRepository.findAll(pageable); // 향수 전체 목록 가져오기

		return getFragranceSearchFinalResult(userId, fragrancePage);
	}

	// 사용자 id 와 향수 id 를 받아와 즐겨찾기 테이블에 해댱 향수가 있는지 없는지 확인하는 메서드
	private boolean Like(Long userId, Long fragranceId) {
		return userFragranceRepository.existsByUserIdAndFragranceId(userId, fragranceId);
	}

	// 향수 목록 dto 반환 및 paging 처리 메서드 생성 (중복제거)
	private FragranceResponseDTO.FragranceSearchFinalResult getFragranceSearchFinalResult(Long userId,
		Page<Fragrance> fragrancePage) {
		List<FragranceResponseDTO.FragranceSearchResult> content = fragrancePage.getContent().stream()
			.map(fragrance -> {
				// 즐겨찾기 확인
				boolean liked = (userId != null) && Like(userId, fragrance.getId());
				return FragranceConverter.toSearchResultDto(fragrance, liked);
			})
			.collect(Collectors.toList());

		return FragranceConverter.toSearchFinalResult(content, fragrancePage.hasNext());
	}

	// 메인페이지 향수 추천(Md's Choice) 목록 조회 API
	@Override
	public FragranceResponseDTO.FragranceMdChoiceResult getFragranceMdChoice(CustomUserDetails userDetails) {

		User user = userRepository.findUserByLoginId(userDetails.getUsername())
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		FragranceGender fragranceGender = switch (user.getGender()) {
			case MALE -> FragranceGender.MALE;
			case FEMALE -> FragranceGender.FEMALE;
			case NONE -> null;
		};

		List<Long> noteList = user.getUserNoteList().stream()
			.map(userNote -> userNote.getNote().getId())
			.toList();

		List<Fragrance> userMdChoice = fragranceRepository
			.findByUserMdChoice((fragranceGender == null ? null : fragranceGender.name()), noteList);
		return getFragranceMdChoiceFinalResult(user.getId(), userMdChoice, user.getName(), user.getNickname());
	}

	// Md's Choice 목록에 즐겨찾기 정보 포함해서 최종 DTO 반환
	private FragranceResponseDTO.FragranceMdChoiceResult getFragranceMdChoiceFinalResult(
		Long userId, List<Fragrance> fragranceList, String name, String nickname) {

		List<FragranceResponseDTO.FragranceSearchResult> content = fragranceList.stream()
			.map(fragrance -> {
				boolean liked = (userId != null) && Like(userId, fragrance.getId());
				return FragranceConverter.toSearchResultDto(fragrance, liked);
			}).toList();

		return FragranceConverter.toMdChoiceResult(content, name, nickname);
	}
}
