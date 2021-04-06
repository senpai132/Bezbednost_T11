package adminapi.adminaplication.repository;

import adminapi.adminaplication.model.CertificateSignRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificateSignRequestRepository extends JpaRepository<CertificateSignRequest, Integer> {
    List<CertificateSignRequest> findByStatus(int status);
    CertificateSignRequest findById(Long id);
}
