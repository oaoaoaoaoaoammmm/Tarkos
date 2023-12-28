package com.example.tarkos.models;


import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String profession;
    private String firstName;
    private String lastName;
    private Integer age;
    private String username;
    private String password;
}
