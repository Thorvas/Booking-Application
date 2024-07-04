package com.notification.notification.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "notification_saved", groupId = "notification_saved_listeners")
    public void notifyBookingCreated(String message) {
        try {
            Notification notification = objectMapper.readValue(message, Notification.class);
            System.out.println(notification);
            notificationRepository.save(notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
