package com.example.Donate.Controller;

import com.example.Donate.Entity.User;
import com.example.Donate.Service.NotificationService;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @ModelAttribute
    public void addGlobalAttributes(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            int unreadCount = notificationService.countUnread(user);

            model.addAttribute("globalUser", user);
            model.addAttribute("globalUnread", unreadCount);
        }
    }
}
