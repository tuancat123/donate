package com.example.Donate.Controller;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Service.DonationCampaignService;
import com.example.Donate.Service.DonationService;
import com.example.Donate.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Controller
public class HomeController {
    @Autowired
    private DonationService donationsService;

    @Autowired
    private DonationCampaignService campaignService;

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String home(Model model) {
        // Tổng số tiền quyên góp
        BigDecimal totalDonation = donationsService.getTotalDonationAmount2();
        if (totalDonation == null) {
            totalDonation = BigDecimal.ZERO;
        }
        String totalDonationStr = NumberFormat
                .getInstance(new Locale("vi", "VN"))
                .format(totalDonation.longValue()) + " VND";

        // Tổng chiến dịch ACTIVE
        long totalCampaigns = campaignService.getActiveCampaignCount();

        // Tổng user
        long totalUsers = userService.getTotalUsers();

        // Danh sách campaign nổi bật
        List<Donation_Campaigns> campaigns = campaignService.getActiveCampaigns();
        if (campaigns == null) campaigns = List.of();  // tránh null

        model.addAttribute("totalDonation", totalDonationStr);
        model.addAttribute("totalCampaigns", totalCampaigns);
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("campaigns", campaigns);


        return "index";
    }


}
