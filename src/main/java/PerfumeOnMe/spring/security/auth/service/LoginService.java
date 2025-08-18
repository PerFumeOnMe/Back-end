package PerfumeOnMe.spring.security.auth.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;

import PerfumeOnMe.spring.common.enums.Social;
import PerfumeOnMe.spring.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.security.auth.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

	public AuthResponseDTO.LoginResult login(AuthRequestDTO.Login request, HttpServletResponse response) throws
		IOException;

	AuthResponseDTO.LoginResult generateAuthResponse(String loginId,
		Authentication request, Social social, HttpServletResponse response);
}
