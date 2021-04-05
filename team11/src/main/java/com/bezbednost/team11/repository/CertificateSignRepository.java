package com.bezbednost.team11.repository;

import com.bezbednost.team11.model.CertificateSignRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateSignRepository extends JpaRepository<CertificateSignRequest, Integer>{
}
