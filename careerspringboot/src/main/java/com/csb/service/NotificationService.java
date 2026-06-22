package com.csb.service;

import com.csb.dto.NotificationType;
import com.csb.model.Notification;
import com.csb.model.User;
import com.csb.repository.NotificationRepository;
import com.csb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public void createNotification(User user, String title, String message, NotificationType type
    ) {
        Notification notification = new Notification();

        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);

        notificationRepository.save(notification);
    }

    public List<Notification> getNotifications(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return notificationRepository.getNotificationsById(user.getId());
    }

    public long getUnreadCount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return notificationRepository
                .countByUserIdAndIsReadFalse(user.getId());
    }

    public void markAsRead(int notificationId) {
        Notification notification =
                notificationRepository.findById(notificationId)
                        .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}
