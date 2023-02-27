package ru.netology.cloud_api.service.authservice;

import lombok.Getter;
import org.springframework.stereotype.Service;
import ru.netology.cloud_api.dao.UserRepository;
import ru.netology.cloud_api.service.userservice.UserService;

import java.util.HashMap;
import java.util.Map;

@Service
@Getter
public class AuthServiceImpl implements AuthService {
    private final Map<String, String> tokenRepository = new HashMap<>();
    private final UserRepository userRepository;
    private final UserService userService;

    public AuthServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void logoutUser(String token) {
        userRepository.removeToken(token);
    }

}
