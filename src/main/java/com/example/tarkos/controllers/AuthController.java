package com.example.tarkos.controllers;


import com.example.tarkos.dtos.UserDto;
import com.example.tarkos.services.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Authorization controller",
        description = "All about authorization."
)
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Authorization user")
    @PostMapping
    public ResponseEntity<UserDto> authorization(
            @RequestBody
            @NotNull
            @Parameter(description = "User dto", required = true)
            UserDto userDto
    ) {
        userDto = userService.authorize(userDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }
}
