package com.example.Donate.Service;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Repository.DonationCampaignRepository;
import com.example.Donate.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DonationCampaignService {

    @Autowired
    private DonationCampaignRepository donationCampaignRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Donation_Campaigns> getAllCampaigns(){
        return donationCampaignRepository.findAll();
    }

    public List<Donation_Campaigns> getActiveCampaigns(){
        return donationCampaignRepository.findByStatus(Donation_Campaigns.Status.ACTIVE);
    }
    public List<Donation_Campaigns> getCampaignsByOrganization(Integer orgId) {
        return donationCampaignRepository.findByOrganization_OrgId(orgId);
    }

    public Donation_Campaigns saveCampaign(Donation_Campaigns campaign) {
        return donationCampaignRepository.save(campaign);
    }

    public Donation_Campaigns getCampaignById(Integer id){
        return donationCampaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chiến dịch nào có ID: " + id));
    }

    public void deleteCampaign(Integer id){

        // Xóa notification liên quan
        notificationRepository.deleteByCampaign_CampaignId(id);

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



    public List<Donation_Campaigns> getCampaignsByStatus(Donation_Campaigns.Status status) {
        return donationCampaignRepository.findByStatus(status);
    }

    public long getActiveCampaignCount() {
        return donationCampaignRepository.countByStatus(Donation_Campaigns.Status.ACTIVE);
    }

}
