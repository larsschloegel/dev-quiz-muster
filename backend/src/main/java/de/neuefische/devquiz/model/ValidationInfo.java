package de.neuefische.devquiz.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationInfo {
    private String questionID;
    private String answerID;
}
