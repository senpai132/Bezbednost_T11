import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
    private fb:FormBuilder
  ) {
    this.form = this.fb.group({
      username: ['',Validators.required],
      password: ['',Validators.required]
    });
  }

  ngOnInit(): void {
  }

  validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
  } 

  login() {
    const val = this.form.value;
    this.wrongUsernameOrPass = false;

    if (val.username && val.password) {
      if (this.validateEmail(val.username)){
        this.invalidData = false;
        alert("should be loged in");
      }else{
        alert("Email not valid!");
      }
    }
    else{
      this.invalidData = true;
      this.errorMessage = "Both username and password are required";
  }
}

}

