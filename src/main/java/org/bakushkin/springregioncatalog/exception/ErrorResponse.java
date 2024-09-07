package org.bakushkin.springregioncatalog.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Сведения об ошибке")
public class ErrorResponse {

    private final String message;

    private final String reason;

    private final String status;

    private final String timestamp;
}
