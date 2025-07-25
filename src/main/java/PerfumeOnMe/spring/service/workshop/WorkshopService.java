package PerfumeOnMe.spring.service.workshop;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.converter.WorkshopConverter;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.domain.Workshop;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.repository.workshop.WorkshopRepository;
import PerfumeOnMe.spring.service.user.UserService;
import PerfumeOnMe.spring.web.dto.workshop.WorkshopResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkshopService {
	private final WorkshopRepository workshopRepository;
	private final UserRepository userRepository;
	private final UserService userService;

	@Transactional(readOnly = true)
	public List<WorkshopResponseDTO.WorkshopListResponseDTO> findAllWorkshopsByUser(CustomUserDetails userDetails) {

		Long userId = userDetails.getUserId();

		// 유저 ID 검증
		if (userId == null) {
			throw new GeneralException(ErrorStatus.USER_ID_NULL);
		}

		// 유저 존재 여부 검증
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));

		// 응답생성
		List<Workshop> workshops = workshopRepository.findAllByUser(user);

		return WorkshopConverter.toWorkshopListResponse(workshops);
	}

}
