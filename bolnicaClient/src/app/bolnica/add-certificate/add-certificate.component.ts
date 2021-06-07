import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { CertificateService } from '../services/certificate.service';

@Component({
  selector: 'app-add-certificate',
  templateUrl: './add-certificate.component.html',
  styleUrls: ['./add-certificate.component.sass']
})
export class AddCertificateComponent implements OnInit {

  commonName: string;
  lastName: string;
  firstName: string;
  organization: string;
  organizationUnit: string;
  country: string;
  email: string;
  locality: string;
  serialNumber: string;

  constructor(
    private service: CertificateService,
    private toastr: ToastrService
    ) { 
    this.commonName = "";
    this.lastName = "";
    this.firstName = "";
    this.organization = "";
    this.organizationUnit = "";
    this.country = "";
    this.email = "";
    this.locality = "";
    this.serialNumber = "";
  }

  ngOnInit(): void {
  }

  someEmpty(): boolean{
    if (this.commonName == ""){
      return true;
    }
    if (this.country == ""){
      return true;
    }
    if (this.lastName == ""){
      return true;
    }
    if (this.firstName == ""){
      return true;
    }
    if (this.organization == ""){
      return true;
    }
    if (this.organizationUnit == ""){
      return true;
    }
    if (this.email == ""){
      return true;
    }
    if (this.locality == ""){
      return true;
    }
    if (this.serialNumber == ""){
      return true;
    }
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  } 

  addRequest(){
    if (this.someEmpty()){
      this.toastr.warning("Some fields remain empty!", "Warning");
    }
    else if (!this.validateEmail(this.email)){
      this.toastr.error("Email is not valid!", "Error");
    }
    else if (!/^\d+$/.test(this.serialNumber)){
      this.toastr.error("Serial number can only contain digits", "Error")
    }
    else{
      this.service.sendRequest({
        "commonName": this.commonName,
        "lastName": this.lastName,
        "firstName": this.firstName,
        "organization": this.organization,
        "organizationUnit": this.organizationUnit,
        "country": this.country,
        "email": this.email,
        "locality": this.locality,
        "serialNumber": this.serialNumber
      }).subscribe(res => {
        console.log("response: " + res);
        this.toastr.success("Certificate request sent", "Success");
      }, err => {
        console.log(err);
        this.toastr.error("Something went wrong!", "Server error");
      });
    }
  }

}
