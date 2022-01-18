package de.thb.ejc.repository;

import de.thb.ejc.entity.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = QRCode.class, idClass = Integer.class)
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    Optional<QRCode> findById(int id);
    //ToDo mit Jonas überprüfen
    @Query("SELECT u FROM qrcode qr INNER JOIN user u ON u.id = qr.user.id WHERE u.uid =: uid")
    Optional<QRCode> findByUid(String uid);
}
