package com.csb.model;

import com.csb.dto.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String title;

    private String message;

    private boolean isRead = false;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;
}
