package PerfumeOnMe.spring.service.user;

import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

	UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request);

	AuthResponseDTO.RefreshToken reissue(String refreshToken, HttpServletResponse response);

	void logout(HttpServletRequest request);
}
