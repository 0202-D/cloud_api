package ru.netology.cloud_api.service.userservice;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.netology.cloud_api.dao.UserRepository;
import ru.netology.cloud_api.entity.UserDB;


import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getUserByLogin(String login) {
        UserDB userDB = userRepository.findByLogin(login);
        if (userDB == null) {
            return null;
        }
        return new User(userDB.getLogin(), userDB.getPassword(), new ArrayList<>());
    }

    public void addTokenToUser(String login, String token) {
        userRepository.addTokenToUser(login, token);
    }

}
