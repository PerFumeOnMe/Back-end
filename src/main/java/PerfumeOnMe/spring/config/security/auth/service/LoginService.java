package PerfumeOnMe.spring.config.security.auth.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import PerfumeOnMe.spring.config.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import PerfumeOnMe.spring.domain.enums.Social;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

	public AuthResponseDTO.LoginResult login(AuthRequestDTO.Login request, HttpServletResponse response) throws
		IOException;

	AuthResponseDTO.LoginResult generateAuthResponse(String loginId,
		Authentication request, Social social, HttpServletResponse response);
}
