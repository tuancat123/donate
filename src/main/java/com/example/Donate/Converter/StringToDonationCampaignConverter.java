package com.example.Donate.Converter;

import com.example.Donate.Entity.Donation_Campaigns;
import com.example.Donate.Repository.DonationCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToDonationCampaignConverter implements Converter<String, Donation_Campaigns> {

    @Autowired
    private DonationCampaignRepository campaignRepository;

    @Override
    public Donation_Campaigns convert(String source) {
        if(source == null || source.isEmpty()) {
            return null;
        }
        try {
            Integer id = Integer.valueOf(source);
            return campaignRepository.findById(id).orElse(null);
        } catch(NumberFormatException e) {
            return null;
        }
    }
}

