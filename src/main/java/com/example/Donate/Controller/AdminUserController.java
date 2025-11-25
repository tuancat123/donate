package com.example.Donate.Controller;

import com.example.Donate.Entity.User;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    // Xem danh sách người dùng
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users"; // → templates/admin/users.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "user-add"; // → templates/admin/user-add.html
    }

    // ------------------ LƯU NGƯỜI DÙNG MỚI ------------------
    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/users";
    }

    // ------------------ FORM SỬA ------------------
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        User existingUser = userService.getUserById(id);
        if (existingUser == null) return "redirect:/admin/users";

        model.addAttribute("user", existingUser);
        return "user-edit"; // → templates/admin/user-edit.html
    }

    // ------------------ CẬP NHẬT NGƯỜI DÙNG ------------------
    @PostMapping("/update/{id}")
    public String updateUser(
            @PathVariable("id") Integer id,
            @ModelAttribute("user") User user
    ) {
        User existing = userService.getUserById(id);
        if (existing == null) return "redirect:/admin/users";

        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setRole(user.getRole());
        existing.setEnabled(user.isEnabled());

        // Nếu bạn muốn cập nhật password:
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existing.setPassword(user.getPassword());
        }

        userService.saveUser(existing);
        return "redirect:/admin/users";
    }

    // Xóa người dùng
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    // Bật/Tắt người dùng
    @GetMapping("/toggle/{id}")
    public String toggleUser(@PathVariable("id") Integer id) {
        User user = userService.getUserById(id);
        if (user != null) {
            if (user.isEnabled()) userService.disableUser(id);
            else userService.enableUser(id);
        }
        return "redirect:/admin/users";
    }
}
