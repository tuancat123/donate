package com.example.Donate.Service;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Entity.Notification;
import com.example.Donate.Entity.User;
import com.example.Donate.Repository.NotificationRepository;
import com.example.Donate.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Notification> getUnreadNotifications(User user) {
        return notificationRepository.findByUserAndSeenFalse(user);
    }

    public void createNotification(User user, Donation_Campaigns campaign, String message) {
        Notification notif = new Notification();
        notif.setUser(user);
        notif.setCampaign(campaign);
        notif.setMessage(message);
        notificationRepository.save(notif);
    }

    public Notification findById(Integer id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public void markAsSeen(Notification notification) {
        notification.setSeen(true);
        notificationRepository.save(notification);
    }


    //Hàm đếm thông báo
    public int countUnread(User user) {
        return notificationRepository.findByUserAndSeenFalse(user).size();
    }

    //Thông báo tất cả các user
    public void notifyAllUsers(Donation_Campaigns campaign) {
        List<User> users = userRepository.findAll();

        for (User u : users) {
            Notification n = new Notification();
            n.setUser(u);
            n.setCampaign(campaign);
            n.setMessage("Một chiến dịch mới vừa được tạo: " + campaign.getTitle());
            notificationRepository.save(n);
        }
    }
}
