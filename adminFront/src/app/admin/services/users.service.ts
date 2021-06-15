import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private readonly port = "https://localhost:8080/";
  private readonly admins = "c";
  private readonly doctors = "hospitaldoctor";
  private readonly toDoctor = "/changetodoctor/";
  private readonly toAdmin = "/changetoadmin/";

  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getAllAdmins(): Observable<User[]> {
    return this.http.get<User[]>(this.port + this.admins, { headers: this.headers, responseType: 'json' });
  }

  getAllDoctors(): Observable<User[]> {
    return this.http.get<User[]>(this.port + this.doctors, { headers: this.headers, responseType: 'json' });
  }

  deleteAdmin(id: number): Observable<any>{
    return this.http.delete<any>(this.port + this.admins + "/" + id, { headers: this.headers, responseType: 'json' });
  }

  addAdmin(user: User): Observable<any>{
    return this.http.post<any>(this.port + this.admins, user, { headers: this.headers, responseType: 'json' });
  }

  deleteDoctor(id: number): Observable<any>{
    return this.http.delete<any>(this.port + this.doctors + "/" + id, { headers: this.headers, responseType: 'json' });
  }

  addDoctor(user: User): Observable<any>{
    return this.http.post<any>(this.port + this.doctors, user, { headers: this.headers, responseType: 'json' });
  }

  changeToDoctor(id: number): Observable<any>{
    return this.http.put<any>(this.port + this.admins + this.toDoctor + id, { headers: this.headers, responseType: 'json' });
  }

  changeToAdmin(id: number): Observable<any>{
    return this.http.put<any>(this.port + this.doctors + this.toAdmin + id, { headers: this.headers, responseType: 'json' });
  }

}
