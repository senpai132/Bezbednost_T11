import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Patient } from '../model/patient';
import { PatientsService } from '../services/patients.service';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.sass']
})
export class PatientsComponent implements OnInit {

  patients: Patient[];

  constructor(
    private toastr: ToastrService,
    private patientsService: PatientsService
  ) { }

  ngOnInit(): void {
    this.patientsService.allPatients().subscribe( res => {
      this.patients = res;
    })
  }

  view(id: number){
    console.log(id);
  }

}
