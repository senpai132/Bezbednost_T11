import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { CertificateRequest } from '../../model/certificate-request';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-certificate-requests',
  templateUrl: './certificate-requests.component.html',
  styleUrls: ['./certificate-requests.component.sass']
})
export class CertificateRequestsComponent implements OnInit {

  @ViewChild('type', { static: false }) private type;

  closeResult = '';
  acceptId: number;
  requests: CertificateRequest[];
  form:FormGroup;

  constructor(
    private fb:FormBuilder,
    private modalService: NgbModal,
    private CertificateService: CertificateService,
    private toastr: ToastrService,
    private router: Router
    ) {
      this.form = this.fb.group({
        leaf: ['',Validators.required],
        device: ['',Validators.required]
      });
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
    this.acceptId = null;
    console.log("revokedId: "+this.acceptId);
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  accept(id){
    if (this.acceptId == null){
      this.acceptId = id;
      this.open(this.type);
    }
  }

  confirm(){
    const val = this.form.value;
    if (val.leaf == "" && val.device == ""){
      this.toastr.warning("One box must be checked!", "Warning");
    }
    else{
      this.modalService.dismissAll();
      this.CertificateService.acceptRequest(this.acceptId).subscribe(res => {
        this.toastr.info("Request accepted", "Info");
        this.router.navigateByUrl('/certificates');
      });
    }
  }

  decline(id){
    this.CertificateService.declineRequest(id).subscribe(res => {
      this.toastr.info("Request declined", "Info");
      this.saveChange();
    });
  }

}
