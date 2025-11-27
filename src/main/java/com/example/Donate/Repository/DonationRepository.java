package com.example.Donate.Repository;

import com.example.Donate.Entity.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donations, Integer> {
    @Query("""
        SELECT d.campaign.campaignId, d.campaign.title, SUM(d.amount), COUNT(d)
        FROM Donations d
        GROUP BY d.campaign.campaignId, d.campaign.title
        """)
    List<Object[]> getStatsByCampaign();

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donations d")
    BigDecimal sumAllDonations();


    @Query("""
        SELECT d FROM Donations d 
        WHERE d.user.userId = :userId 
        ORDER BY d.donatedAt DESC
    """)
    List<Donations> findByUserIdOrderByDateDesc(Integer userId);
}


