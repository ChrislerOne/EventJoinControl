package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = Event.class, idClass = Long.class)
public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findById(int id);

}
