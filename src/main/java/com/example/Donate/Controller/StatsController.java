package com.example.Donate.Controller;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Repository.DonationCampaignRepository;
import com.example.Donate.Repository.DonationRepository;
import com.example.Donate.Service.DonationService;
import com.example.Donate.Service.DonationCampaignService;
import com.example.Donate.Service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationCampaignRepository campaignRepository;

    @GetMapping
    public String stats(Model model) {

        // 1️⃣ Các số tổng quát
        model.addAttribute("totalAmount", statsService.getTotalDonationAmount());
        model.addAttribute("totalDonations", statsService.countDonations());
        model.addAttribute("activeCampaigns", statsService.countActiveCampaigns());

        // 2️⃣ Danh sách thống kê từng chiến dịch
        List<Map<String, Object>> campaignStats = statsService.getCampaignStats();
        model.addAttribute("campaignStats", campaignStats);

        // 3️⃣ Dữ liệu cho ChartJS
        model.addAttribute("campaignNames", campaignStats.stream()
                .map(c -> c.get("title"))
                .collect(Collectors.toList()));
        model.addAttribute("campaignAmounts", campaignStats.stream()
                .map(c -> ((Number)c.get("currentAmount")).longValue())
                .collect(Collectors.toList()));

        return "stats"; // templates/admin/stats.html
    }
}
