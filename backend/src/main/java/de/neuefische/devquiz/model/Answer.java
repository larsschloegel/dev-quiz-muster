package de.neuefische.devquiz.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Answer {
    private String id;
    private String answerText;
    private Boolean correct;
}
