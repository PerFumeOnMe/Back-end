package PerfumeOnMe.spring.service.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import PerfumeOnMe.spring.apiPayload.code.status.ErrorStatus;
import PerfumeOnMe.spring.apiPayload.exception.GeneralException;
import PerfumeOnMe.spring.converter.UserConverter;
import PerfumeOnMe.spring.domain.User;
import PerfumeOnMe.spring.repository.user.UserRepository;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 사용자 회원가입
	@Override
	public UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request) {

		// RequestDTO 값 추출하기
		String name = request.getName();
		String loginId = request.getLoginId();
		String password = request.getPassword();
		String passwordConfirm = request.getPasswordConfirm();

		/*
		비즈니스 로직 검증
		1. loginId 중복 확인
		2. password와 passwordConfirm 일치 여부 확인
		 */
		Optional<User> findUser = userRepository.findUserByLoginId(loginId);
		if (findUser.isPresent()) {
			throw new GeneralException(ErrorStatus.LOGIN_ID_DUPLICATE);
		}
		if (!password.equals(passwordConfirm)) {
			throw new GeneralException(ErrorStatus.PASSWORD_CONFIRM_FAIL);
		}

		// 사용자 정보 엔티티 변환 및 DB 저장
		User newUser = UserConverter
			.toSignupUser(name, loginId, passwordEncoder.encode(password));
		userRepository.save(newUser);

		// 사용자 회원가입 결과를 ResponseDTO로 응답
		return UserConverter.toSignupResult(newUser);
	}
}
