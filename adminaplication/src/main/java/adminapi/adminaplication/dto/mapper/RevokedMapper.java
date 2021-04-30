package adminapi.adminaplication.dto.mapper;

import adminapi.adminaplication.dto.response.RevokedCertificateDTO;
import adminapi.adminaplication.model.RevokedCertificate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RevokedMapper implements MapperInterface<RevokedCertificate, RevokedCertificateDTO>{
    @Override
    public RevokedCertificate toEntity(RevokedCertificateDTO dto) {

        return null;
    }

    @Override
    public List<RevokedCertificate> toEntityList(List<RevokedCertificateDTO> dtoList) {
        List<RevokedCertificate> revokedCertificates = new ArrayList<>();
        for (RevokedCertificateDTO dto : dtoList){
            revokedCertificates.add(this.toEntity(dto));
        }
        return revokedCertificates;
    }

    @Override
    public RevokedCertificateDTO toDto(RevokedCertificate entity) {
        RevokedCertificateDTO revokedCertificateDTO = new RevokedCertificateDTO();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        revokedCertificateDTO.setRevocationDate(formatter.format(entity.getRevocationDate()));
        revokedCertificateDTO.setSerialNumber(entity.getSerialNumber());
        revokedCertificateDTO.setId(entity.getId());
        revokedCertificateDTO.setRevocationReason(entity.getRevocationReason());
        return revokedCertificateDTO;
    }

    @Override
    public List<RevokedCertificateDTO> toDtoList(List<RevokedCertificate> entityList) {
        List<RevokedCertificateDTO> dtoList = new ArrayList<>();
        for (RevokedCertificate revokedCertificate : entityList){
            dtoList.add(this.toDto(revokedCertificate));
        }
        return dtoList;
    }
}
