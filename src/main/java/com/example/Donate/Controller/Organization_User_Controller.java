package com.example.Donate.Controller;

import com.example.Donate.Entity.Organizations;
import com.example.Donate.Service.DonationCampaignService;
import com.example.Donate.Service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/organizations")
public class Organization_User_Controller {

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DonationCampaignService donationCampaignService;

    @GetMapping
    public String viewOrganizations(Model model) {
        List<Organizations> organizations = organizationService.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organization_view"; // templates/user/organizations.html
    }

    @GetMapping("/{id}")
    public String viewOrganizationDetail(@PathVariable Integer id, Model model) {
        Organizations org = organizationService.getOrganizationById(id);
        if (org == null) {
            return "redirect:/organizations";
        }

        model.addAttribute("organization", org);
        model.addAttribute("campaigns", donationCampaignService.getCampaignsByOrganization(id));
        return "organization-detail";
    }
}
