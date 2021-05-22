import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.sass']
})
export class LoginComponent implements OnInit {

  public wrongUsernameOrPass:boolean;
  public errorMessage = "";
  public invalidData = false;
  form:FormGroup;

  constructor(
    private fb:FormBuilder,
    private loginService: AuthService,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required]
    });
  }

  ngOnInit(): void {
  }

  login() {
    const val = this.form.value;
    this.wrongUsernameOrPass = false;

    if (val.username && val.password) {
      this.invalidData = false;
      console.log("username: "+val.username);
      console.log("password: "+val.password);
      this.loginService.login(val.username, val.password)
          .subscribe(
              (loggedIn:boolean) => {
                console.log("loggedIn = true");
                  if(loggedIn){
                    console.log("Should be logged in");
                    this.router.navigateByUrl('/main');
                  }
              },
              (err:Error) => {
                if(err.toString()==='Ilegal login'){
                  this.wrongUsernameOrPass = true;
                  console.log(err);
                }
                else{
                  throwError(err);
                }
                alert("Incorrect username or password!");
              }
      ); 
    }
    else{
      this.invalidData = true;
      this.errorMessage = "Both username and password are required";
  }
}

}

