import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { CertificateRequest } from '../model/certificate-request';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly port = 'http://localhost:8080/api';
  private readonly request = "/certificate-sign-request";
  private readonly acceptReq = "/accept/";
  private readonly declineReq = "/decline/";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private http: HttpClient) { }

  allRequests(): Observable<CertificateRequest[]> {
    return this.http.get<CertificateRequest[]>(this.port + this.request, { headers: this.headers, responseType: 'json' });
  }

  sendRequest(request: CertificateRequest): Observable<CertificateRequest> {
    return this.http.post<CertificateRequest>(this.port + this.request, request, { headers: this.headers, responseType: 'json' });
  }

  acceptRequest(id: number): Observable<any> {
    return this.http.put<any>(this.port + this.request + this.acceptReq + id, { headers: this.headers, responseType: 'json' });
  }

  declineRequest(id: number): Observable<any> {
    return this.http.put<any>(this.port + this.request + this.declineReq + id, { headers: this.headers, responseType: 'json' });
  }

}

