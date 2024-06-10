package com.notification.notification.exceptionHandler;

import java.util.Date;

public record ExceptionDetails(
        Date timestamp,
        String message,
        String details,
        String exitCode
) {
}
