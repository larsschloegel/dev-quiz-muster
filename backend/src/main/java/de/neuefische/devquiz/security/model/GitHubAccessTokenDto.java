package de.neuefische.devquiz.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubAccessTokenDto {

    @JsonProperty("access_token")
    private String accessToken;
}
