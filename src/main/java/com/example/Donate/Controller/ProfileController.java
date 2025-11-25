package com.example.Donate.Controller;

import com.example.Donate.Entity.User;
import com.example.Donate.Repository.UserRepository;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String viewProfile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        User user = userService.findByUsername(username);

        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String fullName,
            @RequestParam String email,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        User user = userService.getUserByUsername(principal.getName());
        userService.updateProfile(user, fullName, email);

        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin thành công!");
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {

        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không khớp!");
            return "redirect:/profile";
        }

        User user = userService.getUserByUsername(principal.getName());
        boolean ok = userService.changePassword(user, oldPassword, newPassword);

        if (!ok) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Đổi mật khẩu thành công!");
        }

        return "redirect:/profile";
    }
}
