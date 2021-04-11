import { Component, OnInit } from '@angular/core';
import {CertificateRequest} from '../model/certificate-request';
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

  constructor(private service: CertificateService) { 
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

  addRequest(){
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
      alert("Certificate request sent");
    }, err => {
      console.log(err);
      alert("Something went wrong!");
    });
  }
}
