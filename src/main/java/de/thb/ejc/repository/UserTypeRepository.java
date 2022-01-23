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

//    @Query("SELECT ut FROM usertypes ut INNER JOIN user u ON ut.id = u.userType.id WHERE u.uid = :uid")
//    Optional<UserType> findByUid(@Param("uid") String uid);
}
