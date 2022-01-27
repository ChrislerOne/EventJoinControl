package de.thb.ejc.service;

import de.thb.ejc.entity.Event;
import de.thb.ejc.entity.OrgaUserType;
import de.thb.ejc.entity.Organization;
import de.thb.ejc.form.organization.EditOrganizationForm;
import de.thb.ejc.form.organization.OrganizationForm;
import de.thb.ejc.repository.OrgaUserTypeRepository;
import de.thb.ejc.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrgaUserTypeService {

    @Autowired
    private OrgaUserTypeRepository orgaUserTypeRepository;


    public ArrayList<OrgaUserType> getAllOrgsByUser(int id) {
        return orgaUserTypeRepository.findOrgsByUser(id);
    }

    public ArrayList<OrgaUserType> getUsersByOrg(int id) {
        return orgaUserTypeRepository.findUsersByOrg(id);
    }


}
