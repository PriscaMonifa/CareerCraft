package com.csb.repository;

import com.csb.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("""
       select n
       from Notification n
       where n.user.id = ?1
       order by n.createdAt desc
       """)
    List<Notification> getNotificationsById(int id);;

    long countByUserIdAndIsReadFalse(int id);
}
