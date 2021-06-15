import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LogEntry } from '../model/log-entry';
import { LogReport } from '../model/log-report';

@Injectable({
  providedIn: 'root'
})
export class LoggerService {

  private readonly port = "https://localhost:8081/api/logger";
  private readonly code = "/code/";
  private readonly type = "/type/";
  private readonly date = "/dateinterval";
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

  findByDates(report: LogReport): Observable<LogEntry[]>{
    return this.http.put<LogEntry[]>(this.port + this.date, report, { headers: this.headers, responseType: 'json' });
  }
}
