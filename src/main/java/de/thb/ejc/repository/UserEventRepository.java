package de.thb.ejc.repository;

import de.thb.ejc.entity.User;
import de.thb.ejc.entity.UserEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = UserEvent.class, idClass = Long.class)
public interface UserEventRepository extends CrudRepository<UserEvent, Long> {
    Optional<UserEvent> findById(long id);

    @Query("SELECT ue from user_events ue WHERE ue.userId.id = :userid AND ue.eventId.id = :eventid")
    UserEvent getSpecificUserEvent(@Param("userid") int userId, @Param("eventid") int eventid);

}

