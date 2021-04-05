package com.bezbednost.team11.repository;

import com.bezbednost.team11.model.CertificateSignRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CertificateSignRepository extends JpaRepository<CertificateSignRequest, Integer>{

}
