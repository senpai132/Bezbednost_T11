import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';
import { CertificateService } from '../services/certificate.service';
import { RuleService } from '../services/rule.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.sass']
})
export class MainPageComponent implements OnInit {

  @ViewChild('reason', { static: false }) private reason;
  @ViewChild('rule', { static: false }) private rule;

  closeResult = '';
  revReason: string;
  isAdmin: boolean;

  minVal: number;
  maxVal: number;
  useFunc: string;

  constructor(
    private modalService: NgbModal,
    private loginService: AuthService,
    private toastr: ToastrService,
    private router: Router,
    private CertificateService: CertificateService,
    private ruleService: RuleService
  ) {
    this.isAdmin = loginService.getRole() == "ROLE_ADMIN";
    console.log("role: "+loginService.getRole());
    this.revReason = "";
    this.minVal = 0;
    this.maxVal = 0;
    this.useFunc = "";
   }

  ngOnInit(): void {

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
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  openReason(){
    this.open(this.reason);
  }

  openRule(){
    this.open(this.rule);
  }

  save(){
    if (this.revReason.trim() == ""){
      this.toastr.warning("Reason for revocation must be given!", "Warning");
    }
    else{
      console.log("revoked reason: "+this.revReason);
      this.modalService.dismissAll();
      this.CertificateService.revokeRequest({
        "serialNumber": "",
        "revocationReason": this.revReason
      }).subscribe(
      res => {
        console.log(res);
        this.toastr.info("Certificate revoked", "Info");
      }, err => {
        console.log(err);
        this.toastr.error("Certificate revocation failed", "Error");
      }
      );
    }
  }

  addRule(){
    if (this.useFunc.trim() == "" || this.minVal == 0 || this.maxVal == 0){
      this.toastr.warning("No field can remain empty!", "Warning");
    }
    else if (this.maxVal < this.minVal){
      this.toastr.error("Maximum value cannot be lower than minimum value!", "Error");
    }
    else{
      this.modalService.dismissAll();
      this.ruleService.addRule({
        "maxValue": this.maxVal,
        "minValue": this.minVal,
        "useFunction": this.useFunc.trim()
      }).subscribe(res => {
        console.log(res);
        this.toastr.success("New rule successfully added", "Success");
      }, err => {
        console.log(err);
        this.toastr.success("New rule could not be added", "Error");
      });
    }
  }

  logOut(){
    this.loginService.logout();
    this.router.navigate(['']);
  }

}