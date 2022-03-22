package io.beesports.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BSException extends RuntimeException {

    private String message;
    private Integer code;
    private String additionalMessage;

    public BSException(BSError error) {
        this.message = error.getMessage();
        this.code = error.getCode();
    }

    public BSException(BSError error, String additionalMessage) {
        this.message = error.getMessage() + " - " + additionalMessage;
        this.code = error.getCode();
    }
}
