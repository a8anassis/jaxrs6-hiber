package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dto.UserLoginDTO;
import gr.aueb.cf.schoolapp.service.IUserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationProvider {

    private final IUserService userService;

    public boolean authenticate(UserLoginDTO loginDTO) {
        return userService.isUserValid(loginDTO.getUsername(), loginDTO.getPassword());
    }
}


