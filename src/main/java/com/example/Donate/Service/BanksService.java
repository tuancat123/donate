package com.example.Donate.Service;

import com.example.Donate.Entity.Banks;
import com.example.Donate.Repository.BanksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BanksService {
    @Autowired
    private BanksRepository banksRepository;
    public List<Banks> getAllBanks(){
        return banksRepository.findAll();
    }
}
