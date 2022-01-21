package de.thb.ejc.repository;

import de.thb.ejc.entity.Event;
import de.thb.ejc.entity.State;
import de.thb.ejc.entity.User;
import de.thb.ejc.entity.UserType;
import de.thb.ejc.form.EventStateOrgaHelper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT n FROM user n WHERE n.id = :id")
    Optional<User> findById(@Param("id") int id);


    @Query("select u.state from user u INNER JOIN states s on s.id = u.state.id WHERE u.qrToken = :qrToken")
    Optional<State> findStateByQrToken(@Param("qrToken") String qrToken);


    @Query("SELECT u.userType FROM user u WHERE u.uid = :uid")
    Optional<UserType> findUserType(@Param("uid") String uid);

    @Query(value = "SELECT o.name as orgname, e.name as eventname, s.name as sname FROM user u " +
            "JOIN user_events ue ON u.id = ue.userId " +
            "JOIN events e ON ue.eventId = e.id " +
            "JOIN organizations o ON e.organizationId = o.id  " +
            "JOIN event_state es ON e.id = es.eventId " +
            "JOIN states s ON es.stateId = s.id " +
            "WHERE u.id = :id", nativeQuery = true)
    Optional<EventStateOrgaHelper> findAllEventsFromUser(@Param("id") int id);
}


// TODO: Möglicherweise muss das abgeändert werden! Beachte Auth.