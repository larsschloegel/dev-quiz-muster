package de.neuefische.devquiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class Answer {
    private String id;
    private String answerText;
    private Boolean correct;

    private String createUUID() {
        return UUID.randomUUID().toString();
    }
}
