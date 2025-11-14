package com.example.Donate.Service;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Repository.DonationCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DonationCampaignService {

    @Autowired
    private DonationCampaignRepository donationCampaignRepository;

    public List<Donation_Campaigns> getALlCampaigns(){
        return donationCampaignRepository.findAll();
    }

    public List<Donation_Campaigns> getActiveCampaigns(){
        return donationCampaignRepository.findByStatus(Donation_Campaigns.Status.ACTIVE);
    }
    public List<Donation_Campaigns> getCampaignsByOrganization(Integer orgId) {
        return donationCampaignRepository.findByOrganization_OrgId(orgId);
    }

    public void saveCampaign(Donation_Campaigns campaign) {
        donationCampaignRepository.save(campaign);
    }

    public Donation_Campaigns getCampaignById(Integer id){
        return donationCampaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chiến dịch nào có ID: " + id));
    }

    public void deleteCampaign(Integer id){
         donationCampaignRepository.deleteById(id);
    }

    public void addDonation(Integer campaignId, BigDecimal amount) {
        Donation_Campaigns campaign = donationCampaignRepository.findById(campaignId).orElseThrow();
        campaign.setCurrentAmount(campaign.getCurrentAmount().add(amount));

        // nếu đã đạt mục tiêu
        if (campaign.getCurrentAmount().compareTo(campaign.getGoalAmount()) >= 0) {
            campaign.setStatus(Donation_Campaigns.Status.COMPLETED);
        }

        donationCampaignRepository.save(campaign);
    }
}
