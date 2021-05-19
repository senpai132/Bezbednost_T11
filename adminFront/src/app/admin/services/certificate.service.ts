import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { Certificate } from '../model/certificate';
import { CertificateRequest } from '../model/certificate-request';
import { Revocation } from '../model/revocation';
import { RevokedCertificate } from '../model/revoked-certificate';

@Injectable({
  providedIn: 'root'
})
export class CertificateService {

  private readonly port = "http://localhost:8080/api";
  private readonly request = "/certificate-sign-request";
  private readonly path = "/certificate";
  private readonly acceptReq = "/accept/";
  private readonly declineReq = "/decline/";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getAll(): Observable<Certificate[]> {
    return this.http.get<Certificate[]>(this.port + this.path, { headers: this.headers, responseType: 'json' });
  }

  revoke(revocation: Revocation):  Observable<any> {
    return this.http.put<any>(this.port + this.path, revocation, { headers: this.headers, responseType: 'json' });
  }

  getRevoked(): Observable<RevokedCertificate[]>{
    return this.http.get<RevokedCertificate[]>(this.port + this.path + '/removed', { headers: this.headers, responseType: 'json' });
  }

  allRequests(): Observable<CertificateRequest[]> {
    return this.http.get<CertificateRequest[]>(this.port + this.request, { headers: this.headers, responseType: 'json' });
  }

  acceptRequest(id: number): Observable<any> {
    return this.http.put<any>(this.port + this.request + this.acceptReq + id, { headers: this.headers, responseType: 'json' });
  }

  declineRequest(id: number): Observable<any> {
    return this.http.put<any>(this.port + this.request + this.declineReq + id, { headers: this.headers, responseType: 'json' });
  }

}
