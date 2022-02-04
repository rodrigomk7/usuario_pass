package com.coderhouse.service.impl;

import com.coderhouse.builder.UserBuilder;
import com.coderhouse.handle.ApiRestException;
import com.coderhouse.model.document.User;
import com.coderhouse.model.request.UserRequest;
import com.coderhouse.model.response.UserResponse;
import com.coderhouse.repository.UserRepository;
import com.coderhouse.security.JwtProvider;
import com.coderhouse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse getUser(UserRequest request) throws ApiRestException {

        var user = getByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw ApiRestException.builder().message("El usuario o el password es inválido").build();
        }
        var token = jwtProvider.getJWTToken(request.getUsername());
        return UserResponse.builder().username(request.getUsername()).token(token).build();
    }

    @Override
    public UserResponse register(UserRequest request) throws ApiRestException {
        validateUser(request);
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        UserBuilder.requestToDocument(request);
        var user = repository.save(UserBuilder.requestToDocument(request));
        return UserBuilder.documentToResponse(user);

    }

    void validateUser(UserRequest request) throws ApiRestException {
        var user = getByUsername(request.getUsername());
        if (user != null) {
            throw ApiRestException.builder().message("El usuario ya existe").build();
        }
        user = repository.findUserByEmail(request.getEmail());
        if (user != null) {
            throw ApiRestException.builder().message("El correo ya se encuentra registrado").build();
        }
    }

    private User getByUsername(String username) {
        return repository.findUserByUsername(username);
    }
}
