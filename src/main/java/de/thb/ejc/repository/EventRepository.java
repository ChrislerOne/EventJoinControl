package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;

import de.thb.ejc.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = Event.class, idClass = Long.class)
public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findById(int id);

    @Query("select e from events e WHERE e.name = :name")
    Optional<Event> findEventByName(@Param("name") String name);

    @Query("select e from events e WHERE e.organizationId.id = :organizationid")
    ArrayList<Event> findEventsByOrganization(@Param("organizationid") int organizationId);

    @Query("select u from user u join user_events ue on u.id = ue.userId.id where ue.eventId.id = :eventId")
    ArrayList<User> findAllUserFromEvent(@Param("eventId") int eventId);

}
