package de.thb.ejc.repository;

import de.thb.ejc.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT n FROM user n WHERE n.id = :id")
    Optional<User> findById(@Param("id") int id);

    //ToDo Query
    @Query("select n from user n")
    static Optional<User> findStateByName(@Param("user") String name) {
        //ToDo return type
        return null;
    }
}

// KEINE GARANTIE, DASS ES FUNKTIONIERT!
//    SELECT s.name FROM user AS u
//        INNER JOIN user_events AS ue on u.id = ue.userId
//        INNER JOIN event_state AS es on ue.eventId = es.eventId
//        INNER JOIN states AS s on es.stateId = s.id
//    WHERE u.id = 1

// TODO: Möglicherweise muss das abgeändert werden! Beachte Auth.