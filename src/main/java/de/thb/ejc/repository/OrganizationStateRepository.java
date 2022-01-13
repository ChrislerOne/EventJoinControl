package de.thb.ejc.repository;

import de.thb.ejc.entity.OrganizationState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = OrganizationState.class, idClass = Long.class)
public interface OrganizationStateRepository extends CrudRepository<OrganizationState, Long> {
    Optional<OrganizationState> findById(long id);
}
