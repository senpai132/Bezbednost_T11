import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LogEntry } from '../model/log-entry';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  private readonly port = "https://localhost:8081/api/logger";
  private readonly code = "/code/";
  private readonly type = "/type/";
  private headers = new HttpHeaders({ 'Content-Type': 'application/json'});

  constructor(private http: HttpClient) { }

  getAll(): Observable<LogEntry[]>{
    return this.http.get<LogEntry[]>(this.port, { headers: this.headers, responseType: 'json' });
  }

  findByCode(code: string): Observable<LogEntry[]>{
    return this.http.get<LogEntry[]>(this.port + this.code + code, { headers: this.headers, responseType: 'json' });
  }

  findByType(type: string): Observable<LogEntry[]>{
    return this.http.get<LogEntry[]>(this.port + this.type + type, { headers: this.headers, responseType: 'json' });
  }
}
