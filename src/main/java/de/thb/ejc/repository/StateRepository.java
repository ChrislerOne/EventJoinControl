package de.thb.ejc.repository;

import de.thb.ejc.entity.State;
import de.thb.ejc.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.ArrayList;
import java.util.Optional;

@RepositoryDefinition(domainClass = State.class, idClass = Long.class)
public interface StateRepository extends CrudRepository<State, Long> {
    Optional<State> findById(int id);

    //Returns ArrayList of Users with given status
    @Query("SELECT n from states st INNER JOIN user n ON st.id = n.state.id where st.name=: state")
    ArrayList<User> findUserByState(String state);
}
