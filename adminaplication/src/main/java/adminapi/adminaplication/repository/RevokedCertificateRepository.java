package adminapi.adminaplication.repository;

import adminapi.adminaplication.model.RevokedCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedCertificateRepository extends JpaRepository<RevokedCertificate, Integer> {
    Optional<RevokedCertificate> findBySerialNumber(String serialNumber);
}
