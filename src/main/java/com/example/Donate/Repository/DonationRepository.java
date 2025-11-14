package com.example.Donate.Repository;

import com.example.Donate.Entity.Donations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donations, Integer> {

}


