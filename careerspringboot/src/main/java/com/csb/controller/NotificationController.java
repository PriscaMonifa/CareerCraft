package com.csb.controller;

import com.csb.model.Notification;
import com.csb.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/receive")
    public List<Notification> getNotifications(Principal principal) {
        return notificationService.getNotifications(
                principal.getName()
        );
    }
    @GetMapping("/unread-count")
    public long getUnreadCount(Principal principal) {
        return notificationService.getUnreadCount(principal.getName());
    }

    @PutMapping("/read/{notificationId}")
    public void markAsRead(@PathVariable int notificationId) {
        notificationService.markAsRead(notificationId);
    }
}
