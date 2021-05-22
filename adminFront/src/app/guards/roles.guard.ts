import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../admin/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class RolesGuard implements CanActivate {
  
  constructor(private auth: AuthService, private router: Router) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.auth.validateToken();
    if (this.auth.isLoggedIn()) {
      const expectedRoles: string = next.data.expectedRoles;
      const roles: string[] = expectedRoles.split('|', 2);

      console.log(roles.indexOf(this.auth.getCurrentUser().authorities[0]));
      if (roles.indexOf(this.auth.getCurrentUser().authorities[0]) === -1) {
        this.router.navigate(['']);
        return false;
      }

      return true;
    } else {
      this.router.navigate(['']);
      return false;
    }
  }
  
}
