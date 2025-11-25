package com.example.Donate.Service;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Entity.Donations;
import com.example.Donate.Entity.User;
import com.example.Donate.Repository.DonationCampaignRepository;
import com.example.Donate.Repository.DonationRepository;
import com.example.Donate.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationsRepository;

    @Autowired
    private DonationCampaignRepository campaignRepository;

    public List<Donations> getAllDonations() {
        return donationsRepository.findAll();
    }

    public Donations saveDonation(Donations donation) {
        Donations savedDonation = donationsRepository.save(donation);

        // Cập nhật tổng tiền đã quyên góp của chiến dịch
        Donation_Campaigns campaign = savedDonation.getCampaign();
        if (campaign != null) {
            BigDecimal current = campaign.getCurrentAmount() != null
                    ? campaign.getCurrentAmount() : BigDecimal.ZERO;
            BigDecimal newAmount = current.add(savedDonation.getAmount());

            campaign.setCurrentAmount(newAmount);
            campaignRepository.save(campaign);
        }
        return savedDonation;
    }

    public void deleteDonation(Integer donationId) {
        Optional<Donations> optionalDonation = donationsRepository.findById(donationId);
        if (optionalDonation.isPresent()) {
            Donations donation = optionalDonation.get();
            Donation_Campaigns campaign = donation.getCampaign();

            if (campaign != null && campaign.getCurrentAmount() != null) {
                BigDecimal current = campaign.getCurrentAmount();
                BigDecimal newAmount = current.subtract(donation.getAmount());
                if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                    newAmount = BigDecimal.ZERO;
                }

                campaign.setCurrentAmount(newAmount);
                campaignRepository.save(campaign);
            }

            donationsRepository.delete(donation);
        }
    }



                            //Thống kê//
    public long getTotalDonationAmount() {
        Long sum = donationsRepository.sumAllDonations();
        return sum != null ? sum : 0;
    }

    public long countDonations() {
        return donationsRepository.count();
    }

    public List<Map<String, Object>> getStatisticsByCampaign() {

        List<Object[]> rows = donationsRepository.getStatsByCampaign();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", row[0]);
            map.put("amount", row[1]);
            map.put("donations", row[2]);
            result.add(map);
        }

        return result;
    }

}
