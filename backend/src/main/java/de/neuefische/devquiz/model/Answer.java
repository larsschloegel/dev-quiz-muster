package de.neuefische.devquiz.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {
    private String id ;
    private String answerText;
    private Boolean correct;
}
