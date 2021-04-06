package adminapi.adminaplication.service;

import adminapi.adminaplication.model.CertificateSignRequest;
import adminapi.adminaplication.repository.CertificateSignRequestRepository;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.List;

@Service
public class CertificateSignRequestService {

    @Autowired
    private CertificateSignRequestRepository certificateSignRequestRepository;

    public List<CertificateSignRequest> findAllPendingRequest(){
        List<CertificateSignRequest> pendingRequest = certificateSignRequestRepository.findByStatus(0);
        List<CertificateSignRequest> renewalRequest = certificateSignRequestRepository.findByStatus(1);

        pendingRequest.addAll(renewalRequest);
        return pendingRequest;
    }

    private String getCRSX509NameField(X500Name x500Name, ASN1ObjectIdentifier field) throws Exception {
        RDN[] rdn = x500Name.getRDNs(field);

        if (rdn.length == 0)
            return "";
        else if (rdn.length != 1)
            throw new Exception(
                    "CertificateSigningRequestService: RDN expected only one param. (given: " + rdn.length + ")");

        return IETFUtils.valueToString(rdn[0].getFirst().getValue());
    }

    public CertificateSignRequest createRequest(byte[] req) throws Exception{

        PKCS10CertificationRequest csr = this.extractCertificationRequest(req);

        X500Name x500Name = csr.getSubject();

        String commonName = getCRSX509NameField(x500Name, BCStyle.CN);
        String lastName = getCRSX509NameField(x500Name, BCStyle.SURNAME);
        String firstName = getCRSX509NameField(x500Name, BCStyle.GIVENNAME);
        String organization = getCRSX509NameField(x500Name, BCStyle.O);
        String organizationUnit = getCRSX509NameField(x500Name, BCStyle.OU);
        String country = getCRSX509NameField(x500Name, BCStyle.C);
        String email = getCRSX509NameField(x500Name, BCStyle.E);
        String serialNumber = getCRSX509NameField(x500Name, BCStyle.SERIALNUMBER);
        String locality = getCRSX509NameField(x500Name, BCStyle.L);

        certificateSignRequestRepository.save(
                new CertificateSignRequest(
                        commonName,
                        lastName,
                        firstName,
                        organization,
                        organizationUnit,
                        country,
                        locality,
                        email,
                        0,
                        serialNumber
                        ));
        return null;
    }

    private PKCS10CertificationRequest extractCertificationRequest(byte[] rawRequest) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(rawRequest);
        Reader pemReader = new BufferedReader(new InputStreamReader(bis));
        PEMParser pemParser = new PEMParser(pemReader);

        Object parsedObj = pemParser.readObject();
        if (parsedObj instanceof PKCS10CertificationRequest) {
            return (PKCS10CertificationRequest) parsedObj;
        }

        throw new IOException();
    }

    public void declineRequest(Integer id) {
        CertificateSignRequest certificateSignRequest = certificateSignRequestRepository.findById(id).orElse(null);

        certificateSignRequest.setStatus(3);
        certificateSignRequestRepository.save(certificateSignRequest);
    }

    public CertificateSignRequest acceptRequest(Integer id) {
        CertificateSignRequest certificateSignRequest = certificateSignRequestRepository.findById(id).orElse(null);

        certificateSignRequest.setStatus(2);
        certificateSignRequestRepository.save(certificateSignRequest);
        return certificateSignRequest;
    }
}
