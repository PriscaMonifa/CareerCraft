package com.csb.controller;

import com.csb.dto.LoginResponseDto;
import com.csb.dto.TokenDto;
import com.csb.model.User;
import com.csb.service.UserService;
import com.csb.utility.JwtUtility;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    public final UserService userService;
    public final JwtUtility jwtUtility;

                       //-----LOGIN-----
    @GetMapping("/login")
    public TokenDto login(Principal principal) {
        String username = principal.getName();
        String token = jwtUtility.generateToken(username);
        return new TokenDto(username, token);
    }

    @GetMapping("/user-details")
    public LoginResponseDto getUserDetails(Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getRole().toString()
        );


    }
}
