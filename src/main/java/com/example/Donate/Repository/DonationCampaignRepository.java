package com.example.Donate.Repository;

import com.example.Donate.DonateApplication;
import com.example.Donate.Entity.Donation_Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DonationCampaignRepository extends JpaRepository<Donation_Campaigns, Integer> {
    List<Donation_Campaigns> findByStatus(Donation_Campaigns.Status status);
    List<Donation_Campaigns> findByOrganization_OrgId(Integer orgId);
    long countByStatus(Donation_Campaigns.Status status);
}
