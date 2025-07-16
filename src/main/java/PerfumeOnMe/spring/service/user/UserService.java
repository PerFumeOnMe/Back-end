package PerfumeOnMe.spring.service.user;

import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.config.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request);

	AuthResponseDTO.LoginResult reissue(String refreshToken, HttpServletResponse response);

	String logout(HttpServletRequest request);

	void deleteUser(HttpServletRequest request);

	void onboarding(UserRequestDTO.Onboarding request, CustomUserDetails userDetails);

	void updateUserNote(UserRequestDTO.UserNoteUpdate request, CustomUserDetails userDetails);
}
