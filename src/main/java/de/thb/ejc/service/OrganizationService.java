package de.thb.ejc.service;

import de.thb.ejc.entity.*;
import de.thb.ejc.form.organization.EditOrganizationForm;
import de.thb.ejc.form.organization.OrganizationForm;
import de.thb.ejc.repository.OrgaUserTypeRepository;
import de.thb.ejc.repository.OrganizationRepository;
import de.thb.ejc.repository.OrganizationStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrganizationService {
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationStateRepository organizationStateRepository;

    @Autowired
    private OrgaUserTypeRepository orgaUserTypeRepository;

    public Organization getOrganizationById(int id) {
        return organizationRepository.findById(id).get();
    }

    public void addOrganization(OrganizationForm organizationForm, User user, UserType userType) {
        Organization organization = new Organization();
        organization.setName(organizationForm.getName());
        organization.setDescription(organizationForm.getDescription());
        organizationRepository.save(organization);

        OrgaUserType orgaUserType = new OrgaUserType();
        orgaUserType.setUser(user);
        orgaUserType.setOrganization(organization);
        orgaUserType.setUserType(userType);
        orgaUserTypeRepository.save(orgaUserType);
    }

    public void editOrganization(EditOrganizationForm editOrganizationForm) {
        Organization currentOrganization = organizationRepository.findEventByName(editOrganizationForm.getOldname()).get();
        String newname = editOrganizationForm.getNewname();
        currentOrganization.setName(newname);
        organizationRepository.save(currentOrganization);
    }

    public void deleteOrganization(int organizationid) {
        Organization currentOrganization = organizationRepository.findById(organizationid).get();
        organizationRepository.delete(currentOrganization);
    }

    public ArrayList<Organization> getAllOrganizations() {
        return (ArrayList<Organization>) organizationRepository.findAll();
    }

    public ArrayList<OrganizationState> getStatesByOrganizationId(int organizationId) {
        if (!organizationStateRepository.findStatesByOrganizationId(organizationId).isEmpty()) {
            return organizationStateRepository.findStatesByOrganizationId(organizationId);
        }
        return null;
    }
}
