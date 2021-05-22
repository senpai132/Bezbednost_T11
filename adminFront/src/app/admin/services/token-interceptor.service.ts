import { HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService {

  constructor(private inj: Injector) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const authenticationService: AuthService = this.inj.get(AuthService);
    authenticationService.validateToken();
    const token = authenticationService.getToken();

    if (token) {
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    if (authenticationService.isLoggedIn() &&
        !authenticationService.isRefreshing()) {
      this.checkTokenExpireDate(authenticationService);
    }

    return next.handle(request);
  }

  checkTokenExpireDate(authService: AuthService): void {
    const milliesLeft = authService.getCurrentUser().expireIn - new Date().getTime();
    if (milliesLeft >= 0 && milliesLeft < 9000000) {
        authService.setRefreshing(true);
        authService.refreshToken().subscribe(
          res => {
            authService.setRefreshing(false);
          },
          error => {
            authService.setRefreshing(false);
          }
        );
    }
  }
}