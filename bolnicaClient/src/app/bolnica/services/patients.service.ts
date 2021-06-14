import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Patient } from '../model/patient';

@Injectable({
  providedIn: 'root'
})
export class PatientsService {

  private readonly port = "https://localhost:8081/api/patient";
  private readonly byId = "/id/";
  private readonly byEmail = "/email/";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  allPatients(): Observable<Patient[]>{
    return this.http.get<Patient[]>(this.port, { headers: this.headers, responseType: 'json' });
  }

  findById(id: number): Observable<Patient>{
    return this.http.get<Patient>(this.port + this.byId + id, { headers: this.headers, responseType: 'json' });
  }

  findByEmail(email: string): Observable<Patient>{
    return this.http.get<Patient>(this.port + this.byEmail + email, { headers: this.headers, responseType: 'json' });
  }

}
