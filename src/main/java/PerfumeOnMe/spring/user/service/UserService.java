package PerfumeOnMe.spring.user.service;

import PerfumeOnMe.spring.fragrance.web.dto.FragranceRequestDTO;
import PerfumeOnMe.spring.fragrance.web.dto.FragranceResponseDTO;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.security.auth.userDetails.CustomUserDetails;
import PerfumeOnMe.spring.user.web.dto.UserRequestDTO;
import PerfumeOnMe.spring.user.web.dto.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request);

	AuthResponseDTO.LoginResult reissue(String refreshToken, HttpServletResponse response);

	String logout(HttpServletRequest request);

	void deleteUser(HttpServletRequest request);

	void onboarding(UserRequestDTO.Onboarding request, CustomUserDetails userDetails);

	void updateUserNote(UserRequestDTO.UserNoteUpdate request, CustomUserDetails userDetails);

	UserResponseDTO.MyPageProfileResponse getUserProfile(Long userId);

	FragranceResponseDTO.FragranceSearchFinalResult getFavoriteFragrances(
		FragranceRequestDTO.FragranceAllRequest request, Long userId);

	void updateProfileImage(Long userId, String imageUrl);
}
