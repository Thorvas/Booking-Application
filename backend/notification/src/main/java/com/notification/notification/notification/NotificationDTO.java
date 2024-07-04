package com.notification.notification.notification;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {

    private String message;
    private LocalDateTime timestamp;
    private Long userId;
}
