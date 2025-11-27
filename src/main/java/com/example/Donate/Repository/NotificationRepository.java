package com.example.Donate.Repository;

import com.example.Donate.Entity.Notification;
import com.example.Donate.Entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUserAndSeenFalse(User user);
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
    @Transactional
    void deleteByCampaign_CampaignId(Integer campaignId);
}
