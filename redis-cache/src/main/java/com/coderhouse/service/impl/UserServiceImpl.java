package com.coderhouse.service.impl;

import com.coderhouse.handle.ApiRestException;
import com.coderhouse.model.User;
import com.coderhouse.repository.UserRepository;
import com.coderhouse.security.JwtProvider;
import com.coderhouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtProvider jwtProvider;

    @Override
    public User getUser(String username, String pwd) throws ApiRestException {
        var user = repository.findUserByUsername(username);

        if (Objects.isNull(user) || !(user.getUsername().equals(username) && user.getPassword().equals(pwd))) {
            throw ApiRestException.builder().message("El usuario o el password es inválido").build();
        }
        var token = jwtProvider.getJWTToken(username);
        return User.builder().username(username).token(token).build();
    }

    @Override
    public User register(User user) {
        //Validar password
        //Encriptar
        //Validar que no exista el usuario
        return repository.save(user);
    }
}
