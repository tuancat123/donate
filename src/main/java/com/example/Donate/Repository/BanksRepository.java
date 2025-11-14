package com.example.Donate.Repository;

import com.example.Donate.Entity.Banks;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanksRepository extends JpaRepository<Banks, Integer> {
}
