package adminapi.adminaplication.dto.mapper;

import adminapi.adminaplication.dto.response.CertificateDTO;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.x500.X500Principal;

public class CertificateMapper implements MapperInterface<X509Certificate, CertificateDTO>{


    @Override
    public X509Certificate toEntity(CertificateDTO dto) {
        return null;
    }

    @Override
    public List<X509Certificate> toEntityList(List<CertificateDTO> dtoList) {
        return null;
    }

    @Override
    public CertificateDTO toDto(X509Certificate entity) {
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setSerialNumber(entity.getSerialNumber());
        Date startDate = entity.getNotBefore();
        Date endDate = entity.getNotAfter();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        certificateDTO.setStartDate(formatter.format(startDate));
        certificateDTO.setEndDate(formatter.format(endDate));

        X500Principal principalSubject = entity.getSubjectX500Principal();
        X500Name x500subject = new X500Name( principalSubject.getName() );
        RDN cnSubject = x500subject.getRDNs(BCStyle.CN)[0];

        certificateDTO.setSubjectName(IETFUtils.valueToString(cnSubject.getFirst().getValue()));

        X500Principal principalIssuer = entity.getIssuerX500Principal();

        X500Name x500issuer = new X500Name( principalIssuer.getName() );
        RDN cnIssuer = x500issuer.getRDNs(BCStyle.CN)[0];
        certificateDTO.setIssuerName(IETFUtils.valueToString(cnIssuer.getFirst().getValue()));

        return certificateDTO;
    }

    @Override
    public List<CertificateDTO> toDtoList(List<X509Certificate> entityList) {
        List<CertificateDTO> certificateDTOS = new ArrayList<>();
        for (X509Certificate certificate : entityList){
            certificateDTOS.add(this.toDto(certificate));
        }
        return certificateDTOS;
    }
}
