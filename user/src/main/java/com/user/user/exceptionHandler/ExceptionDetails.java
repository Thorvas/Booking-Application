package com.user.user.exceptionHandler;

import java.util.Date;

public record ExceptionDetails(
        Date timestamp,
        String message,
        String details,
        String exitCode
) {
}
