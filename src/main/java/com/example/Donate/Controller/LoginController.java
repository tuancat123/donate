package com.example.Donate.Controller;

import com.example.Donate.Entity.User;
import com.example.Donate.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

//    @Autowired
//    private UserService userService;
//
//    // Hiển thị form đăng nhập
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new User());
//        return "login"; // => tạo file templates/login.html
//    }
//
//    // Xử lý đăng nhập
//    @PostMapping("/login")
//    public String processLogin(@RequestParam("username") String username,
//                               @RequestParam("password") String password,
//                               HttpSession session,
//                               Model model) {
//
//        User user = userService.findByUsernameAndPassword(username, password);
//
//        if (user != null) {
//            session.setAttribute("loggedUser", user); // ✅ lưu user vào session
//            return "redirect:/donate"; // quay về trang quyên góp
//        } else {
//            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu sai!");
//            return "login";
//        }
//    }
//
//    // Đăng xuất
//    @GetMapping("/logout")
//    public String logout(HttpSession session) {
//        session.invalidate(); // ✅ xóa session
//        return "redirect:/login";
//    }
}
