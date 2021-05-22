import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.sass']
})
export class MainPageComponent implements OnInit {

  isAdmin: boolean;

  constructor(
    private loginService: AuthService,
    private router: Router
  ) {
    this.isAdmin = loginService.getRole() == "ROLE_ADMIN";
    console.log("role: "+loginService.getRole());
   }

  ngOnInit(): void {

  }

  logOut(){
    this.loginService.logout();
    this.router.navigate(['']);
  }

}