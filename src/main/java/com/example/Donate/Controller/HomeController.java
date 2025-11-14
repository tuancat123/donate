package com.example.Donate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Xin chào! Đây là website quyên góp từ thiện đầu tiên của bạn.");
        return "index";  // trả về file index.html
    }
}
