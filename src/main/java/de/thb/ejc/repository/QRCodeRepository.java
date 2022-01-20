package de.thb.ejc.repository;

import de.thb.ejc.entity.QRCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@RepositoryDefinition(domainClass = QRCode.class, idClass = Integer.class)
public interface QRCodeRepository extends CrudRepository<QRCode, Integer> {
    Optional<QRCode> findById(int id);

    @Query("SELECT qr.file FROM qrcode qr INNER JOIN user u ON u.id = qr.user.id WHERE u.uid = :uid")
    Optional<String> findByUid(@Param("uid") String uid);
}
