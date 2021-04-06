package adminapi.adminaplication.repository;

import adminapi.adminaplication.model.CertificateSignRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateSignRequestRepository extends JpaRepository<CertificateSignRequest, Integer> {


}
