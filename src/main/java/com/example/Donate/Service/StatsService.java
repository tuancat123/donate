package com.example.Donate.Service;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Repository.DonationCampaignRepository;
import com.example.Donate.Repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private DonationCampaignRepository campaignRepository;

    /** Tổng số tiền quyên góp */
    public long getTotalDonationAmount() {
        Long sum = donationRepository.sumAllDonations();
        return sum != null ? sum : 0;
    }

    /** Tổng lượt quyên góp */
    public long countDonations() {
        return donationRepository.count();
    }

    /** Số chiến dịch đang ACTIVE */
    public long countActiveCampaigns() {
        return campaignRepository.findByStatus(Donation_Campaigns.Status.ACTIVE).size();
    }

    /** Lấy danh sách chiến dịch kèm thống kê */
    public List<Map<String, Object>> getCampaignStats() {
        List<Object[]> rows = donationRepository.getStatsByCampaign();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> map = new HashMap<>();
            map.put("campaignId", row[0]);
            map.put("title", row[1]);
            map.put("currentAmount", row[2]);
            map.put("donations", row[3]);
            result.add(map);
        }
        return result;
    }
}
