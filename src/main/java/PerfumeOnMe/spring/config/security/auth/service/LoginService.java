package PerfumeOnMe.spring.config.security.auth.service;

import java.io.IOException;

import PerfumeOnMe.spring.config.security.auth.dto.AuthRequestDTO;
import PerfumeOnMe.spring.config.security.auth.dto.AuthResponseDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {

	public AuthResponseDTO.LoginResult login(AuthRequestDTO.Login request, HttpServletResponse response) throws
		IOException;
}
