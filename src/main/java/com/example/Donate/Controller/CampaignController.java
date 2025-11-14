package com.example.Donate.Controller;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Service.DonationCampaignService;
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

    @GetMapping
    public String viewAllCampaigns(Model model){
        List<Donation_Campaigns> campaigns = donationCampaignService.getActiveCampaigns();
        model.addAttribute("campaigns", campaigns);
        model.addAttribute("organizations", organizationService.getAllOrganizations());
        return "campaigns"; // file HTML trong /templates/admin/
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
        donationCampaignService.saveCampaign(campaign);
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
    public String updateCampaign(@ModelAttribute Donation_Campaigns campaign){
        donationCampaignService.saveCampaign(campaign);
        return "redirect:/admin/campaigns";
    }

    @GetMapping("/delete/{id}")
    public String deleteCampaign(@PathVariable Integer id){
        donationCampaignService.deleteCampaign(id);
        return "redirect:/admin/campaigns";
    }
}

