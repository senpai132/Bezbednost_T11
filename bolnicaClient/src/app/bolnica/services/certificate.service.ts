import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { CertificateRequest } from '../model/certificate-request';
import { Revocation } from '../model/revocation';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly port = "https://localhost:8081/api/";
  private readonly sendReq = "certificate/signingrequest";
  private readonly revokeReq = "cert/revocation";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  sendRequest(request: CertificateRequest): Observable<any> {
    return this.http.post<CertificateRequest>(this.port + this.sendReq, request, { headers: this.headers, responseType: 'json' });
  }

  revokeRequest(revocation: Revocation): Observable<any>{
    return this.http.post<Revocation>(this.port + this.revokeReq, revocation, { headers: this.headers, responseType: 'json' });
  }

}

