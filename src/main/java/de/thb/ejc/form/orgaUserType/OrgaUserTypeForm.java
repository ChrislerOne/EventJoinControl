package de.thb.ejc.form.orgaUserType;

import lombok.Getter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
public class OrgaUserTypeForm {
    String email;
    int organizationId;
    int userTypeId;

}
