package PerfumeOnMe.spring.imagekeyword.util;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.user.domain.User;
import PerfumeOnMe.spring.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 이미지 키워드 기능에서 사용하는 공통 검증 유틸리티
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageKeywordValidationUtils {

	/**
	 * 사용자 존재 여부 검증 및 조회
	 * @param userId 사용자 ID
	 * @param userRepository 사용자 Repository
	 * @return 검증된 사용자 엔티티
	 * @throws GeneralException 사용자가 존재하지 않는 경우
	 */
	public static User validateAndGetUser(Long userId, UserRepository userRepository) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new GeneralException(ErrorStatus.LOGIN_ID_NOT_FOUND));
	}
}