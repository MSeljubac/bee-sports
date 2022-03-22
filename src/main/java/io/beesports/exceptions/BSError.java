package io.beesports.exceptions;

import lombok.Getter;

@Getter
public enum BSError {

    SLEEP_THREAD_INTERRUPTED("Sleep thread interrupted!", 10000),
    API_RATE_EXCEEDED("Hourly API rate exceeded!", 10001),
    API_ERROR("An API error occured!", 10002),
    UNKNOWN_MATCH_TYPE("Unknown match type!", 10003);

    private final String message;
    private final Integer code;

    BSError(String message, Integer code) {
        this.message = message;
        this.code = code;
    }
}
