import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserToken } from '../model/user-token';
import { catchError, map } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  private refreshingToken: boolean;

  constructor(private http: HttpClient) {
  }

  private takeUserFrom(): UserToken {
    let currentUser: UserToken;
    
    if (localStorage.getItem('user')) {
      currentUser = JSON.parse(localStorage.getItem('user'));
    }

    return currentUser;
  }

  validateToken(): void {
    const currentUser: UserToken = this.takeUserFrom();
    if (this.isLoggedIn()) {
      if (new Date().getTime() >= currentUser.expireIn) {
        this.logout();
      }
    }
  }

  login(username: string, password: string): Observable<boolean> {
    // const params:HttpParams = new HttpParams().set('entry',entryText);
    return this.http.post('http://localhost:8081/auth/login', JSON.stringify({ username, password }),
      { headers: this.headers, responseType: 'json' }).pipe(
        map((res: any) => {
          const token = res && res.accessToken;

          if (token) {
            const jwt: JwtHelperService = new JwtHelperService();
            const info = jwt.decodeToken(token);
            console.log(token);
            console.log(info);
            const userToken: UserToken = {
              id: parseInt(info.user_id, 10),
              username: info.sub,
              expireIn: info.exp * 1000,
              authorities: info.roles.map((role) => role.authority),
              token
            };
            localStorage.setItem('user', JSON.stringify(userToken));
            console.log(userToken.authorities[0]);
            return true;
          } else {
            return false;
          }
        }),
        catchError(error => {
          if (error.status === 401) {
            return throwError('Ilegal login');
          } else {
            return throwError('Server error');
          }
        }));
  }

  refreshToken(): Observable<boolean> {
    return this.http.post('http://localhost:8081/auth/refresh', {}).pipe(
      map((res: any) => {
        const token = res && res.accessToken;

        if (token) {
          const jwt: JwtHelperService = new JwtHelperService();
          const info = jwt.decodeToken(token);
          const userToken: UserToken = {
            id: parseInt(info.user_id, 10),
            username: info.sub,
            expireIn: info.exp * 1000,
            authorities: info.roles.map((role) => role.authority),
            token
          };
          localStorage.setItem('user', JSON.stringify(userToken));
          return true;
        } else {
          return false;
        }
      }),
      catchError(error => {
        return throwError('Token could not be refreshed.');
      }));
  }

  isRefreshing(): boolean {
    return this.refreshingToken;
  }

  setRefreshing(value: boolean): void {
    this.refreshingToken = value;
  }

  getUserId(): number {
    const currentUser: UserToken = this.takeUserFrom();
    return currentUser ? currentUser.id : null;
  }

  getToken(): string {
    const currentUser: UserToken = this.takeUserFrom();
    return currentUser ? currentUser.token : null;
  }

  getRole(): string {
    const currentUser: UserToken = this.takeUserFrom();
    return currentUser ? currentUser.authorities[0] : null;
  }

  logout(): void {
    localStorage.removeItem('user');
  }

  isLoggedIn(): boolean {
    if (localStorage.getItem('user')) {
      return true;
    } else {
      return false;
    }
  }

  getCurrentUser(): UserToken {
    return this.takeUserFrom();
  }

}
