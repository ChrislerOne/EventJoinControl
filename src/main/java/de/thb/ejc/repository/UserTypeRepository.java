package de.thb.ejc.repository;

import de.thb.ejc.entity.UserType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = UserType.class, idClass = Integer.class)
public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
    Optional<UserType> findById(int id);
}
