package de.thb.ejc.repository;

import de.thb.ejc.entity.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

@RepositoryDefinition(domainClass = QRCode.class, idClass = Integer.class)
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    Optional<QRCode> findById(int id);

}
