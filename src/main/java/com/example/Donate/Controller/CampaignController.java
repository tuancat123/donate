package com.example.Donate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CampaignController {
    @GetMapping("/campaigns")
    public String showCampaigns(Model model) {
        record Campaign(String title, String description, long goal, long raised, String imageUrl) {}

        List<Campaign> campaigns = List.of(
                new Campaign("Cứu trợ miền Trung", "Hỗ trợ đồng bào bị ảnh hưởng bởi bão lũ", 100_000_000L, 85000000L, "/images/flood.png"),
                new Campaign("Quỹ học bổng 2025", "Tiếp sức học sinh có hoàn cảnh khó khăn", 50000000L, 30000000L, "/images/scholarship.png"),
                new Campaign("Bữa cơm nhân ái", "Tặng suất ăn cho người vô gia cư", 30000000L, 12000000L, "/images/meal.png")
        );

        model.addAttribute("campaigns", campaigns);
        return "campaigns";
    }
}
