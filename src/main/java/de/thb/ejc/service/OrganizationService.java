package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.entity.Organization;
import de.thb.ejc.form.organization.EditOrganizationForm;
import de.thb.ejc.form.organization.OrganizationForm;
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
        organizationRepository.save(organization);
    }

    public void editOrganization(EditOrganizationForm editOrganizationForm){
        Organization currentOrganization = organizationRepository.findEventByName(editOrganizationForm.getOldname()).get();
        String newname = editOrganizationForm.getNewname();
        currentOrganization.setName(newname);
        organizationRepository.save(currentOrganization);
    }

    public void deleteOrganization(int organizationid){
        Organization currentOrganization = organizationRepository.findById(organizationid).get();
        organizationRepository.delete(currentOrganization);
    }


    public ArrayList<Organization> getAllOrganizations() {
        return (ArrayList<Organization>) organizationRepository.findAll();
    }
}
