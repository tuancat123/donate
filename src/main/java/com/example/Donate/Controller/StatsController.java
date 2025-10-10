package com.example.Donate.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
@Controller
public class StatsController {
    @GetMapping("/stats")
    public String showStatsPage(Model model) {
        // Dữ liệu mẫu (sau này bạn thay bằng dữ liệu thật từ DB)
        model.addAttribute("totalAmount", 25000000);
        model.addAttribute("totalDonations", 134);
        model.addAttribute("activeCampaigns", 5);

        model.addAttribute("campaignNames", List.of("Miền Trung", "Học bổng 2025", "Bữa cơm nhân ái"));
        model.addAttribute("campaignAmounts", List.of(10000000, 8000000, 7000000));

        // Bảng thống kê chi tiết
        model.addAttribute("campaignStats", List.of(
                new CampaignStat("Miền Trung", 10000000, 45),
                new CampaignStat("Học bổng 2025", 8000000, 55),
                new CampaignStat("Bữa cơm nhân ái", 7000000, 34)
        ));

        return "stats"; // Tên file HTML trong templates/
    }

    // Lớp phụ cho dữ liệu hiển thị bảng
    public static class CampaignStat {
        public String name;
        public int amount;
        public int donations;

        public CampaignStat(String name, int amount, int donations) {
            this.name = name;
            this.amount = amount;
            this.donations = donations;
        }

        public String getName() { return name; }
        public int getAmount() { return amount; }
        public int getDonations() { return donations; }
    }
}
