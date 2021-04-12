import { Component, OnInit } from '@angular/core';
import { Certificate } from '../../model/certificate';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.sass']
})
export class AllCertificatesComponent implements OnInit {

  certificates: Certificate[];

  constructor(
    private CertificateService: CertificateService,
    ) { }

  ngOnInit(): void {
    this.CertificateService.getAll().subscribe(
      res => {
        this.certificates = res;
      }
    );
  }

  saveChange(): void{
    this.ngOnInit();
  }

  remove(id){
    this.CertificateService.revoke(id).subscribe(res => {
      this.saveChange();
    });
  }

}
