package io.tidy.bigsecretary.user.domain;

import io.tidy.bigsecretary.auth.login.CustomUserDetail;
import io.tidy.bigsecretary.auth.login.LoginErrorCode;
import io.tidy.bigsecretary.common.exception.CommonException;
import io.tidy.bigsecretary.user.exception.UserErrorCode;
import io.tidy.bigsecretary.user.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticatedUserContext {
    private final UserRepository userRepository;

    public User findLoginUser(Authentication authentication) {
        if(!authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new CommonException(LoginErrorCode.REQUIRED_LOGIN);
        }
        CustomUserDetail userDetails = (CustomUserDetail) authentication.getPrincipal();

        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new CommonException(UserErrorCode.NOT_FOUND));
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CommonException(UserErrorCode.NOT_FOUND));
    }
}
