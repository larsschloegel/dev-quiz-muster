package de.neuefische.devquiz.security.controller;

import de.neuefische.devquiz.security.model.ActualUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/user")
public class UserController {

    @GetMapping("me")
    public ActualUser getLoggedInUser(Principal principal){
        return new ActualUser(principal.getName());
    }
}
