package com.notification.notification.notification;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/notification")
public class NotificationController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotifications(){

        return "Get all my bookings.";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getNotification(@PathVariable Long id) {

        return "Return specific booking.";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String createNotification(@RequestBody NotificationDTO notificationDTO) {

        return "Create specific booking";
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateNotification(@PathVariable Long id, @RequestBody NotificationDTO notificationDTO) {

        return "Update specific booking";
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteNotification(@PathVariable Long id) {

        return "Delete specific booking";
    }
}
