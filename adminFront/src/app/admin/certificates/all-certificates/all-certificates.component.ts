import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Certificate } from '../../model/certificate';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.sass']
})
export class AllCertificatesComponent implements OnInit {

  @ViewChild('reason', { static: false }) private reason;

  certificates: Certificate[];
  revokedId: number;
  closeResult = '';
  revReason: string;

  constructor(
    private modalService: NgbModal,
    private CertificateService: CertificateService,
    private router: Router,
    private toastr: ToastrService
    ) {
      this.revReason = '';
     }

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

  open(content) {
    this.modalService.open(content,
   {ariaLabelledBy: "modal-basic-title"}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = 
         `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  getDismissReason(reason: any): string {
    this.revokedId = null;
    console.log("revokedId: "+this.revokedId);
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  giveReason(id){
    if (this.revokedId == null){
      this.revokedId = id;
      this.open(this.reason);
    }
  }

  remove(id){
    this.CertificateService.revoke(id).subscribe(res => {
      this.saveChange();
    });
  }

  save(){
    if (this.revReason.trim() == ""){
      this.toastr.warning("Reason for revocation must be given!", "Error!");
    }
    else{
      console.log("revoked reason: "+this.revReason);
      this.modalService.dismissAll();
      this.CertificateService.revoke({
        "serialNumber": String(this.revokedId),
        "revocationReason": this.revReason
      }).subscribe(
      res => {
        console.log("Certificate revoked");
        this.router.navigateByUrl('/revoked');
      }, err => {
        console.log("Certificate revocation failed");
      }
      );
    }
  }

}
