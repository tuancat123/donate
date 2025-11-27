package com.example.Donate.Controller;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Service.DonationCampaignService;
import com.example.Donate.Service.NotificationService;
import com.example.Donate.Service.OrganizationService;
import com.example.Donate.Service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/campaigns")
public class CampaignController {

    @Autowired
    private DonationCampaignService donationCampaignService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String viewAllCampaigns(Model model){
        List<Donation_Campaigns> campaigns = donationCampaignService.getAllCampaigns();
        model.addAttribute("campaigns", campaigns);
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "campaigns";
    }

    @GetMapping("/add")
    public String showAddCampaignForm(Model model){
        model.addAttribute("campaign", new Donation_Campaigns());
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "add-campaign";
    }

    @PostMapping("/add")
    public String createCampaign(@ModelAttribute Donation_Campaigns campaign, Authentication authentication) {
        campaign.setCurrentAmount(BigDecimal.ZERO);
        campaign.setStartDate(LocalDate.now());
        campaign.setStatus(Donation_Campaigns.Status.ACTIVE);
        campaign.setCreatedBy(userService.getUserByEmail(authentication.getName()));
        //donationCampaignService.saveCampaign(campaign);

        Donation_Campaigns savedCampaign = donationCampaignService.saveCampaign(campaign);

        // 🔔 Gửi thông báo cho toàn bộ user
        notificationService.notifyAllUsers(savedCampaign);

        return "redirect:/admin/campaigns";
    }

    @GetMapping("/edit/{id}")
    public String editCampaign(@PathVariable Integer id, Model model){
        Donation_Campaigns campaign = donationCampaignService.getCampaignById(id);
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        model.addAttribute("campaign", campaign);
        return "edit-campaign";
    }

    @PostMapping("/update")
    public String updateCampaign(@ModelAttribute Donation_Campaigns campaign) {
        Donation_Campaigns existing = donationCampaignService.getCampaignById(campaign.getCampaignId());
        if (existing == null) {
            return "redirect:/admin/campaigns?error";
        }

        // Giữ lại các trường không sửa
        campaign.setStartDate(existing.getStartDate());
        campaign.setCreatedBy(existing.getCreatedBy());
        campaign.setCurrentAmount(existing.getCurrentAmount());

        // Kiểm tra trạng thái
        boolean goalReached = campaign.getGoalAmount() != null &&
                campaign.getCurrentAmount().compareTo(campaign.getGoalAmount()) >= 0;

        boolean expired = campaign.getEndDate() != null && LocalDate.now().isAfter(campaign.getEndDate());

        if (goalReached || expired) {
            campaign.setStatus(Donation_Campaigns.Status.COMPLETED);
        } else {
            campaign.setStatus(Donation_Campaigns.Status.ACTIVE);
        }

        donationCampaignService.saveCampaign(campaign);
        return "redirect:/admin/campaigns";
    }

    @GetMapping("/delete/{id}")
    public String deleteCampaign(@PathVariable Integer id){
        donationCampaignService.deleteCampaign(id);
        return "redirect:/admin/campaigns";
    }
}

