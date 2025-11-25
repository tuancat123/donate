package com.example.Donate.Controller;

import com.example.Donate.Entity.User;
import com.example.Donate.Service.EmailService;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/home")
    public String homePage() {
        return "index"; // trả về file home.html
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
//        try {
//            userService.registerUser(user);
//            return "redirect:/login?success";
//        } catch (RuntimeException e) {
//            model.addAttribute("error", e.getMessage());
//            return "register";
//        }

        try {
            // tạo mã xác thực
            String verificationCode = java.util.UUID.randomUUID().toString();
            user.setVerificationCode(verificationCode);
            user.setEnabled(false); // chưa xác thực
            userService.registerUser(user);

            // gửi email xác thực
            String verifyURL = "http://localhost:8080/verify?code=" + verificationCode;
            String message = "Xin chào " + user.getFullName() + ",\n\n" +
                    "Vui lòng nhấn vào link để xác thực email của bạn:\n" + verifyURL;

            emailService.sendSimpleMessage(user.getEmail(), "Xác thực đăng ký tài khoản", message);

            model.addAttribute("message", "Đăng ký thành công! Vui lòng kiểm tra email để xác thực.");
            return "login";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("code") String code, Model model) {
        User user = userService.findByVerificationCode(code);
        if (user == null) {
            model.addAttribute("error", "Mã xác thực không hợp lệ");
            return "login";
        }
        user.setEnabled(true);
        user.setVerificationCode(null);
        userService.registerUser(user); // lưu lại trạng thái đã kích hoạt

        model.addAttribute("message", "Email đã được xác thực. Bạn có thể đăng nhập.");
        return "login";
    }

}
