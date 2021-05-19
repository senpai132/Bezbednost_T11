import { Component, OnInit } from '@angular/core';
import { RevokedCertificate } from '../../model/revoked-certificate';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-revoked-certificates',
  templateUrl: './revoked-certificates.component.html',
  styleUrls: ['./revoked-certificates.component.sass']
})
export class RevokedCertificatesComponent implements OnInit {

  certificates: RevokedCertificate[];
  closeResult = '';

  constructor(
    private CertificateService: CertificateService
    ) {}

  ngOnInit(): void {
    this.CertificateService.getRevoked().subscribe(
      res => {
        this.certificates = res;
      }
    );
  }

  saveChange(): void{
    this.ngOnInit();
  }

}
