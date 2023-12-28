package com.example.tarkos.services.users;

import com.example.tarkos.dtos.UserDto;

public interface UserService {
    UserDto authorize(UserDto userDto);
}
