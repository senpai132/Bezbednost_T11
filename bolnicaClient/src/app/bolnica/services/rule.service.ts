import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Rule } from '../model/rule';

@Injectable({
  providedIn: 'root'
})
export class RuleService {

  private readonly port = "https://localhost:8081/api/rule";
  private readonly create = "/create";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  addRule(rule: Rule): Observable<any>{
    return this.http.post<any>(this.port + this.create, rule, { headers: this.headers, responseType: 'json' });
  }
}
