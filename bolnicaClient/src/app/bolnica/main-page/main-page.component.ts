import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.sass']
})
export class MainPageComponent implements OnInit {

  @ViewChild('reason', { static: false }) private reason;
  closeResult = '';
  revReason: string;
  isAdmin: boolean;

  constructor(
    private modalService: NgbModal,
    private loginService: AuthService,
    private toastr: ToastrService,
    private router: Router
  ) {
    this.isAdmin = loginService.getRole() == "ROLE_ADMIN";
    console.log("role: "+loginService.getRole());
    this.revReason = "";
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

  save(){
    if (this.revReason.trim() == ""){
      this.toastr.warning("Reason for revocation must be given!", "Warning");
    }
    else{
      this.modalService.dismissAll();
    }
  }

  logOut(){
    this.loginService.logout();
    this.router.navigate(['']);
  }

}