package com.example.Donate.Service;

import com.example.Donate.Entity.Organizations;
import com.example.Donate.Repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organizations> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organizations getOrganizationById(Integer id) {
        return organizationRepository.findById(id).orElse(null);
    }

    public Organizations saveOrganization(Organizations organization) {
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Integer id) {
        organizationRepository.deleteById(id);
    }
}
