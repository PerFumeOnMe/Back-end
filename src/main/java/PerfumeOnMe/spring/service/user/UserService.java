package PerfumeOnMe.spring.service.user;

import PerfumeOnMe.spring.web.dto.user.UserRequestDTO;
import PerfumeOnMe.spring.web.dto.user.UserResponseDTO;

public interface UserService {

	UserResponseDTO.SignupResult signup(UserRequestDTO.Signup request);
}
