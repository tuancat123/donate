package com.example.Donate.scheduler;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Service.DonationCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CampaignScheduler {

    @Autowired
    private DonationCampaignService campaignService;

    @Scheduled(fixedRate = 60000) // chạy mỗi 1 phút
    public void autoCloseCampaigns() {

        List<Donation_Campaigns> activeCampaigns =
                campaignService.getCampaignsByStatus(Donation_Campaigns.Status.ACTIVE);

        for (Donation_Campaigns c : activeCampaigns) {

            boolean goalReached = c.getCurrentAmount().compareTo(c.getGoalAmount()) >= 0;
            boolean expired = c.getEndDate() != null && LocalDate.now().isAfter(c.getEndDate());

            if (goalReached || expired) {
                c.setStatus(Donation_Campaigns.Status.COMPLETED);
                campaignService.saveCampaign(c);
            }
        }
    }
}

