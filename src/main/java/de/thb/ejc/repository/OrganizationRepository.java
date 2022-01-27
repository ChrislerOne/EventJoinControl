package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;
import de.thb.ejc.entity.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = Organization.class, idClass = Long.class)
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findById(int id);

    @Query("select o from organizations o WHERE o.name = :name")
    Optional<Organization> findEventByName(@Param("name") String name);
}
