package com.example.Donate.Controller;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Service.DonationCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/campaigns")
public class Campaign_User_Controller {

    @Autowired
    private DonationCampaignService donationCampaignService;

    // Xem danh sách các chiến dịch đang hoạt động
    @GetMapping
    public String viewCampaignList(Model model) {
        List<Donation_Campaigns> campaigns = donationCampaignService.getAllCampaigns();
        model.addAttribute("campaigns", campaigns);
        return "campaign_view"; // giao diện người dùng (trong templates/user/)
    }

    // Xem chi tiết một chiến dịch
    @GetMapping("/{id}")
    public String viewCampaignDetail(@PathVariable Integer id, Model model) {
        Donation_Campaigns campaign = donationCampaignService.getCampaignById(id);
        if (campaign == null) {
            return "redirect:/campaigns?error=notfound";
        }
        model.addAttribute("campaign", campaign);
        return "campaign-detail";
    }
}

