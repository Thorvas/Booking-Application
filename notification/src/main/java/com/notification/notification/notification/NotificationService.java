package com.notification.notification.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = "booking_created", groupId = "notification_listeners")
    public void notifyBookingCreated(String message) {

    }
}
