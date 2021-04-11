import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { CertificateRequest } from '../model/certificate-request';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly port = "http://localhost:8081/api/certificate";
  private readonly sendReq = "/signingrequest";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  sendRequest(request: CertificateRequest): Observable<any> {
    return this.http.post<CertificateRequest>(this.port + this.sendReq, request, { headers: this.headers, responseType: 'json' });
  }

}

