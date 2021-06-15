import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { ToastrService } from 'ngx-toastr';
import { Patient } from '../model/patient';
import { PatientMessage } from '../model/patient-message';
import { MessagesService } from '../services/messages.service';
import { PatientsService } from '../services/patients.service';

@Component({
  selector: 'app-patients',
  templateUrl: './patients.component.html',
  styleUrls: ['./patients.component.sass']
})
export class PatientsComponent implements OnInit {

  @ViewChild('messages', { static: false }) private messages;

  closeResult = '';
  revReason: string;

  patientId: number;

  patientMessages: PatientMessage[];

  patients: Patient[];

  constructor(
    private modalService: NgbModal,
    private toastr: ToastrService,
    private patientsService: PatientsService,
    private messagesService: MessagesService
  ) { }

  ngOnInit(): void {
    this.patientsService.allPatients().subscribe( res => {
      this.patients = res;
    })
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

  view(id: number){
    this.patientId = id;
    this.messagesService.findByPatient(id).subscribe(res => {
      this.patientMessages = res;
      this.open(this.messages);
    })
  }

}
