package com.example.Donate.Controller;

import com.example.Donate.Entity.Notification;
import com.example.Donate.Entity.User;
import com.example.Donate.Service.NotificationService;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @GetMapping("/notifications")
    public String notifications(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        List<Notification> notifs = notificationService.getUnreadNotifications(user);

        model.addAttribute("user", user);
        model.addAttribute("notifications", notifs);
        return "notifications";
    }


    @PostMapping("/notifications/seen/{id}")
    public String markAsSeen(@PathVariable Integer id,
                             @AuthenticationPrincipal UserDetails userDetails) {

        Notification notif = notificationService.findById(id);
        if (notif != null && notif.getUser().getUsername().equals(userDetails.getUsername())) {
            notificationService.markAsSeen(notif);
        }

        return "redirect:/notifications";
    }
}
