import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { User } from '../model/user';
import { UsersService } from '../services/users.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.sass']
})
export class AllUsersComponent implements OnInit {

  @ViewChild('new_user', { static: false }) private new_user;
  
  closeResult = '';
  revReason: string;

  admins: User[];
  doctors: User[];
  username: String;
  password: String;
  email: String;
  role: String;

  constructor(
    private modalService: NgbModal,
    private toastr: ToastrService,
    private usersService: UsersService
  ) {
    this.username = "";
    this.password = "";
    this.email = "";
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

  removeDoctor(id): void{
    this.usersService.deleteDoctor(id).subscribe(res => {
      console.log(res);
      this.ngOnInit();
      this.toastr.success("Doctor successfully removed", "Success")
    });
  }

  changeDoctor(id): void{
    this.usersService.changeToAdmin(id).subscribe(res => {
      console.log(res);
      this.ngOnInit();
      this.toastr.success("Doctor successfully converted to admin role", "Success")
    });
  }

  removeAdmin(id): void{
    this.usersService.deleteAdmin(id).subscribe(res => {
      console.log(res);
      this.ngOnInit();
      this.toastr.success("Admin successfully removed", "Success")
    });
  }

  changeAdmin(id): void{
    this.usersService.changeToDoctor(id).subscribe(res => {
      console.log(res);
      this.ngOnInit();
      this.toastr.success("Admin successfully converted to doctor role", "Success")
    });
  }

  addUser(): void{
    this.open(this.new_user);
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  } 

  save(): void{
    if (this.username.trim() == "" || this.password.trim() == "" || this.email.trim() == ""){
      this.toastr.warning("All fields are required", "Warning");
    }
    else if (!this.validateEmail(this.email)){
      this.toastr.error("Email not valid!", "Error");
    }
    else{
      if (this.role == "ADMIN"){
        this.usersService.addAdmin({
          "username": this.username,
          "password": this.password,
          "email": this.email
        }).subscribe(res => {
          console.log(res);
          this.modalService.dismissAll();
          this.toastr.success("New admin successfully added", "Success");
          this.ngOnInit();
        }, err => {
          console.log(err);
          this.toastr.error("Username or email already exists", "Error");
        });
      }
      else{
        this.usersService.addDoctor({
          "username": this.username,
          "password": this.password,
          "email": this.email
        }).subscribe(res => {
          console.log(res);
          this.modalService.dismissAll();
          this.toastr.success("New doctor successfully added", "Success");
          this.ngOnInit();
        }, err => {
          console.log(err);
          this.toastr.error("Username or email already exists", "Error");
        });
      }
    }
  }

  ngOnInit(): void {
    this.usersService.getAllAdmins().subscribe(res => {
      this.admins = res;
    });
    this.usersService.getAllDoctors().subscribe(res => {
      this.doctors = res;
    })
  }

}
