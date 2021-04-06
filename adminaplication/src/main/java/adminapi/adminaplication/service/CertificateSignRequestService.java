package adminapi.adminaplication.service;

import adminapi.adminaplication.model.CertificateSignRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class CertificateSignRequestService {

    public List<CertificateSignRequest> findAllPendingRequest(){
        return null;
    }

    public CertificateSignRequest createRequest(){
        return null;
    }

    public void declineRequest(Integer id) {

    }

    public CertificateSignRequest acceptRequest(Integer id) {

        return null;
    }
}
