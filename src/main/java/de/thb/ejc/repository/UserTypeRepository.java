package de.thb.ejc.repository;

import de.thb.ejc.entity.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@RepositoryDefinition(domainClass = UserType.class, idClass = Integer.class)
public interface UserTypeRepository extends CrudRepository<UserType, Integer> {
    Optional<UserType> findById(int id);

    @Query("SELECT ut FROM usertypes ut WHERE ut.name = :name")
    UserType findByName(@Param("name") String name);
}
