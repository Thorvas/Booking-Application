package com.event.event.notification;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NotificationDTO {
    private Long userId;
    private String message;
}
