package com.example.Donate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller
public class DonationController {

    @GetMapping("/donate")
    public String showDonateForm(Model model) {
        model.addAttribute("message", "");
        return "donate";
    }

    @PostMapping("/donate")
    public String processDonation(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam double amount,
            @RequestParam String campaign,
            @RequestParam(required = false) String message,
            Model model) {

        // (Giả lập lưu dữ liệu vào DB)
        System.out.println("Quyên góp từ: " + name + ", " + amount + " cho " + campaign);

        model.addAttribute("message", "Cảm ơn bạn đã ủng hộ chiến dịch \"" + campaign + "\" ❤️");
        return "donate";
    }
}
