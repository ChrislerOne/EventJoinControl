package de.thb.ejc.repository;

import de.thb.ejc.entity.OrganizationState;
import de.thb.ejc.entity.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = OrganizationState.class, idClass = Long.class)
public interface OrganizationStateRepository extends CrudRepository<OrganizationState, Long> {
    Optional<OrganizationState> findById(long id);

    @Query("SELECT os FROM organization_state os INNER JOIN states s ON s.id = os.stateId.id WHERE os.organizationId.id = :organizationId")
    ArrayList<OrganizationState> findStatesByOrganizationId(int organizationId);

    @Query("SELECT os FROM organization_state os INNER JOIN states s ON s.id = os.stateId.id WHERE os.organizationId.id = :organizationId")
    Iterable<OrganizationState> findStatesByOrganizationIdAsIterable(int organizationId);
}


