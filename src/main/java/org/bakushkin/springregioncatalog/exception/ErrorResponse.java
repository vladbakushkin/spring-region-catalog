package org.bakushkin.springregioncatalog.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private final String message;

    private final String reason;

    private final String status;

    private final String timestamp;
}
