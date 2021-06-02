import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/user';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.sass']
})
export class AllUsersComponent implements OnInit {

  @ViewChild('new_user', { static: false }) private new_user;
  
  closeResult = '';
  revReason: string;

  users: User[];
  username: String;
  password: String;
  role: String;

  constructor(
    private modalService: NgbModal,
    private toastr: ToastrService
  ) {
    this.username = "";
    this.password = "";
    this.role = "DOCTOR";
  }

  open(content) {
    this.modalService.open(content,
   {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
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

  remove(id): void{
    this.toastr.info("Should delete user");
  }

  changeRole(id): void{
    this.toastr.info("Should change role");
  }

  addUser(): void{
    this.open(this.new_user);
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  } 

  save(): void{
    if (this.username.trim() == "" || this.password.trim() == ""){
      this.toastr.warning("Both username and password are required");
    }
    else if (!this.validateEmail(this.username)){
      this.toastr.error("Email not valid!");
    }
    else{
      this.modalService.dismissAll();
      this.toastr.info("Should add user");
    }
  }

  ngOnInit(): void {
  }

}
