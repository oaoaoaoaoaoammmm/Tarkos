package com.example.tarkos.mappers;


import com.example.tarkos.dtos.UserDto;
import com.example.tarkos.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto convertToUserDto(User user) {
        if (user == null) {
            return null;
        }

        UserDto.UserDtoBuilder dto = UserDto.builder();

        return dto.id(user.getId())
                .profession(user.getProfession())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .age(user.getAge())
                .username(user.getUsername())
                .build();
    }

    public User convertToUser(UserDto dto) {
        if (dto == null) {
            return null;
        }

        User.UserBuilder user = User.builder();

        return user.firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .profession(dto.getProfession())
                .build();
    }
}
