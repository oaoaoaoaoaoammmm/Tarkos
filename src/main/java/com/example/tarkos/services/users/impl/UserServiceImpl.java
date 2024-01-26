package com.example.tarkos.services.users.impl;


import com.example.tarkos.dtos.UserDto;
import com.example.tarkos.mappers.UserMapper;
import com.example.tarkos.models.User;
import com.example.tarkos.repositories.users.UserRepository;
import com.example.tarkos.services.tokens.TokenService;
import com.example.tarkos.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenService tokenService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
        this.encoder = encoder;
    }

    public UserDto authorize(UserDto userDto) {
        User user = userRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new AuthorizationServiceException("User not found"));

        //encoder.encode(userDto.getPassword())
        if (!user.getPassword().equals(userDto.getPassword())) {
            throw new AuthorizationServiceException("Wrong password");
        }
        
        userDto = userMapper.convertToUserDto(user);
        userDto.setToken(tokenService.generateToken(user.getUsername()));

        return userDto;
    }
}
