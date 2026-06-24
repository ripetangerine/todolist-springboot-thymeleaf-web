package io.github.ripetangerine.todolist.security;

//import io.github.ripetangerine.todolist.utils.ExceptionMessageAccessor;
import io.github.ripetangerine.todolist.exceptions.RegistrationException;
import io.github.ripetangerine.todolist.repository.UserRepository;
import io.github.ripetangerine.todolist.dto.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    private static final String EMAIL_ALREADY_EXISTS = "email_already_exists";

    private static final String USERNAME_ALREADY_EXISTS = "username_already_exists";

    private final UserRepository userRepository;

//    private final ExceptionMessageAccessor exceptionMessageAccessor;

    private void checkUsername(String username) {

        final boolean existsByUsername = userRepository.existsByUsername(username);

        if (existsByUsername) {

            log.warn("Username: {} already being used!", username);

//            final String existsUsername = exceptionMessageAccessor.getMessage(null, USERNAME_ALREADY_EXISTS);
//            throw new RegistrationException(existsUsername);
            throw new RegistrationException(username);
        }

    }

    public void validateUser(RegistrationRequest registrationRequest) {

//        final String email = registrationRequest.getEmail();
        final String username = registrationRequest.getUsername();

//        checkEmail(email);
        checkUsername(username);
    }

}
