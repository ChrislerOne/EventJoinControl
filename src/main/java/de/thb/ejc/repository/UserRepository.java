package de.thb.ejc.repository;

import de.thb.ejc.entity.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT n FROM user n WHERE n.id = :id")
    Optional<User> findById(@Param("id") int id);

    @Query("SELECT n FROM user n WHERE n.uid = :uid")
    Optional<User> findByUid(@Param("uid") String uid);

    @Query("select u.state from user u INNER JOIN states s on s.id = u.state.id WHERE u.qrToken = :qrToken")
    Optional<State> findStateByQrToken(@Param("qrToken") String qrToken);


//    @Query("SELECT u.userType FROM user u WHERE u.uid = :uid")
//    Optional<UserType> findUserType(@Param("uid") String uid);


    @Query("SELECT e FROM events e " +
            "INNER JOIN user_events ue ON e.id = ue.eventId.id " +
            "WHERE ue.userId.uid = :uid")
    ArrayList<Event> findAllEventsFromUser(@Param("uid") String uid);


    @Query("SELECT u from user u WHERE u.email = :email")
    User getUserByEmail(@Param("email") String email);

}

