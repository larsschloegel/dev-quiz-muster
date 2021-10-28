package de.neuefische.devquiz.security.controller;

import de.neuefische.devquiz.security.model.GitHubLoginDto;
import de.neuefische.devquiz.security.service.GitHubLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/github/login")
public class GitHubLoginController {
    private final GitHubLoginService gitHubLoginService;

    public GitHubLoginController(GitHubLoginService gitHubLoginService) {
        this.gitHubLoginService = gitHubLoginService;
    }

    @PostMapping
    public String login(@RequestBody GitHubLoginDto gitHubLoginDto){
        return gitHubLoginService.verifyGitHubCode(gitHubLoginDto.getCode());
    }
}
