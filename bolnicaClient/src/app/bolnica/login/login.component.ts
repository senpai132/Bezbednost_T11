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

  login() {
    const val = this.form.value;
    this.wrongUsernameOrPass = false;

    if (val.username && val.password) {
      this.invalidData = false;
      alert("should be loged in");
    }
    else{
      this.invalidData = true;
      this.errorMessage = "Both username and password are required";
  }
}

}
