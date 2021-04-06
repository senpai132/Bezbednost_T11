package adminapi.adminaplication.dto.mapper;

import adminapi.adminaplication.dto.response.CertificateSignRequestDTO;
import adminapi.adminaplication.model.CertificateSignRequest;

import java.util.ArrayList;
import java.util.List;

public class CertificateSignRequestMapper implements MapperInterface<CertificateSignRequest, CertificateSignRequestDTO>{
    @Override
    public CertificateSignRequest toEntity(CertificateSignRequestDTO dto) {
        CertificateSignRequest certificateSignRequest = new CertificateSignRequest();
        certificateSignRequest.setCommonName(dto.getCommonName());
        certificateSignRequest.setEmail(dto.getEmail());
        certificateSignRequest.setFirstName(dto.getFirstName());
        certificateSignRequest.setLastName(dto.getLastName());
        certificateSignRequest.setCountry(dto.getCountry());
        certificateSignRequest.setLocality(dto.getLocality());
        certificateSignRequest.setOrganization(dto.getOrganization());
        certificateSignRequest.setOrganizationUnit(dto.getOrganizationUnit());

        return certificateSignRequest;
    }

    @Override
    public List<CertificateSignRequest> toEntityList(List<CertificateSignRequestDTO> dtoList) {
        List<CertificateSignRequest> certificateSignRequests = new ArrayList<>();
        for(CertificateSignRequestDTO dto : dtoList){
            certificateSignRequests.add(this.toEntity(dto));
        }
        return certificateSignRequests;
    }

    @Override
    public CertificateSignRequestDTO toDto(CertificateSignRequest entity) {
        CertificateSignRequestDTO certificateSignRequestDTO = new CertificateSignRequestDTO();

        certificateSignRequestDTO.setId(entity.getId());
        certificateSignRequestDTO.setCommonName(entity.getCommonName());
        certificateSignRequestDTO.setEmail(entity.getEmail());
        certificateSignRequestDTO.setCountry(entity.getCountry());
        certificateSignRequestDTO.setLocality(entity.getLocality());
        certificateSignRequestDTO.setFirstName(entity.getFirstName());
        certificateSignRequestDTO.setLastName(entity.getLastName());
        certificateSignRequestDTO.setOrganization(entity.getOrganization());
        certificateSignRequestDTO.setOrganizationUnit(entity.getOrganizationUnit());

        return certificateSignRequestDTO;
    }

    @Override
    public List<CertificateSignRequestDTO> toDtoList(List<CertificateSignRequest> entityList) {
        List<CertificateSignRequestDTO>certificateSignRequestDTOS = new ArrayList<>();
        for(CertificateSignRequest entity : entityList){
            certificateSignRequestDTOS.add(this.toDto(entity));
        }
        return certificateSignRequestDTOS;
    }
}
