package de.neuefische.devquiz.security.controller;

import de.neuefische.devquiz.security.model.AppUser;
import de.neuefische.devquiz.security.service.JWTUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/auth/login")
@EnableAuthorizationServer
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JWTUtilService jwtUtilService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JWTUtilService jwtUtilService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtilService = jwtUtilService;
    }

    @PostMapping
    public String login(@RequestBody AppUser appUser) {

        this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(appUser.getUsername(), appUser.getPassword()));

        //Create a real token!
        return jwtUtilService.createToken(new HashMap<>(), appUser.getUsername());
    }
}
