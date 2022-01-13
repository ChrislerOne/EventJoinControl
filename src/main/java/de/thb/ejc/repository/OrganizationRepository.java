package de.thb.ejc.repository;

import de.thb.ejc.entity.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Organization.class, idClass = Long.class)
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    Optional<Organization> findById(int id);
}
