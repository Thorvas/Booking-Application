package com.payment.payment.exceptionHandler;

import java.util.Date;

public record ExceptionDetails(
        Date timestamp,
        String message,
        String details,
        String exitCode
) {
}
