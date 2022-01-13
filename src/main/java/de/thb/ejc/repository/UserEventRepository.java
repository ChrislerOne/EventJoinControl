package de.thb.ejc.repository;

import de.thb.ejc.entity.UserEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = UserEvent.class, idClass = Long.class)
public interface UserEventRepository extends CrudRepository<UserEvent, Long> {
    Optional<UserEvent> findById(long id);
}
