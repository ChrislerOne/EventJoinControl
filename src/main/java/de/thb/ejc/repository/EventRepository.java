package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = Event.class, idClass = Long.class)
public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findById(int id);

    @Query("select e from events e WHERE e.name = :name")
    Optional<Event> findEventByName(@Param("name") String name);

}
