package de.thb.ejc.repository;

import de.thb.ejc.entity.EventState;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = EventState.class, idClass = Long.class)
public interface EventStateRepository extends CrudRepository<EventState, Long> {
    Optional<EventState> findById(int id);
}
