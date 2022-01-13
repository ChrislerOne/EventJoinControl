package de.thb.ejc.service;

import de.thb.ejc.entity.Organization;
import de.thb.ejc.form.OrganizationForm;
import de.thb.ejc.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    public Organization getOrganizationById(int id) {
        return organizationRepository.findById(id).get();
    }

    public void addOrganization(OrganizationForm organizationForm) {
        Organization organization = new Organization();
        organization.setName(organizationForm.getName());
    }

    public ArrayList<Organization> getAllOrganizations() {
        return (ArrayList<Organization>) organizationRepository.findAll();
    }
}
