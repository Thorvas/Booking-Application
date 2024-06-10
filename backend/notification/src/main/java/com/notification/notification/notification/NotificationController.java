package com.notification.notification.notification;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ws")
public class NotificationController {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/send")
    public String sendTestNotification(HttpServletRequest request, @RequestParam String username, @RequestParam String message) {

        messagingTemplate.convertAndSendToUser("2", "/queue/notifications", new NotificationMessage(message));

        return "Message sent!";
    }

    // Klasa wiadomości
    public static class NotificationMessage {
        private String content;

        public NotificationMessage() {
        }

        public NotificationMessage(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}

