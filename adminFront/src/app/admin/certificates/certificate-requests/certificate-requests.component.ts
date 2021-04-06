import { Component, OnInit } from '@angular/core';
import { CertificateRequest } from '../../model/certificate-request';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-certificate-requests',
  templateUrl: './certificate-requests.component.html',
  styleUrls: ['./certificate-requests.component.sass']
})
export class CertificateRequestsComponent implements OnInit {

  requests: CertificateRequest[];

  constructor(
    private CertificateService: CertificateService,
    ) {
  }

  ngOnInit(): void {
    this.CertificateService.allRequests().subscribe(
      res => {
        this.requests = res;
      }
    );
  }

  saveChange(): void{
    this.ngOnInit();
  }

  accept(id){
    this.CertificateService.acceptRequest(id).subscribe(res => {
      this.saveChange();
    });
  }

  decline(id){
    this.CertificateService.declineRequest(id).subscribe(res => {
      this.saveChange();
    });
  }

}
