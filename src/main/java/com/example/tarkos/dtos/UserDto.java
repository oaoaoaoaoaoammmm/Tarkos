package com.example.tarkos.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@Schema(name = "User dto")
public class UserDto {
    private Integer id;
    private String profession;
    private String firstName;
    private String lastName;
    private Integer age;
    private String username;
    private String password;
    private String token;
}
