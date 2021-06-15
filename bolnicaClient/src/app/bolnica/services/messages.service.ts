import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PatientMessage } from '../model/patient-message';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {

  private readonly port = "https://localhost:8081/api/message";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  allMessages(): Observable<PatientMessage[]>{
    return this.http.get<PatientMessage[]>(this.port, { headers: this.headers, responseType: 'json' });
  }

  findByPatient(id: number): Observable<PatientMessage[]>{
    return this.http.get<PatientMessage[]>(this.port + "/" + id, { headers: this.headers, responseType: 'json' });
  }
}
