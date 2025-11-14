package com.example.Donate.Repository;

import com.example.Donate.Entity.Organizations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organizations, Integer> {

}
