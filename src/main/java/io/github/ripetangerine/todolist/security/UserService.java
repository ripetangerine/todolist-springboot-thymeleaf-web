package io.github.ripetangerine.todolist.security;

import io.github.ripetangerine.todolist.domain.User;
import io.github.ripetangerine.todolist.dto.AuthenticatedUserDto;
import io.github.ripetangerine.todolist.dto.RegistrationRequest;
import io.github.ripetangerine.todolist.dto.RegistrationResponse;
import org.springframework.stereotype.Service;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Service
public interface UserService {

    User findByUsername(String username);

    RegistrationResponse registration(RegistrationRequest registrationRequest);

    AuthenticatedUserDto findAuthenticatedUserByUsername(String username);

}
