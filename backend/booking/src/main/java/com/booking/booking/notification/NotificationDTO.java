package com.booking.booking.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {

    private Long userId;
    private String message;
}
