package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;

import de.thb.ejc.entity.OrgaUserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = OrgaUserType.class, idClass = Long.class)
public interface OrgaUserTypeRepository extends CrudRepository<OrgaUserType, Long> {
    Optional<OrgaUserType> findById(int id);

    @Query("select out FROM usertype_orga_user out WHERE out.user.id = :userid")
    ArrayList<OrgaUserType> findOrgsByUser(@Param("userid") int userId);

}
