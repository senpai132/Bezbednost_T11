import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';  

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddCertificateComponent } from './bolnica/add-certificate/add-certificate.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { LoginComponent } from './bolnica/login/login.component';
import { CertificateService } from './bolnica/services/certificate.service';
import { TokenInterceptorService } from './bolnica/services/token-interceptor.service';
import { MainPageComponent } from './bolnica/main-page/main-page.component';
import { ToastrModule } from 'ngx-toastr';
import { PatientsComponent } from './bolnica/patients/patients.component';
import { TypeLogsComponent } from './bolnica/logs/type-logs/type-logs.component';
import { AllLogsComponent } from './bolnica/logs/all-logs/all-logs.component';
import { CodeLogsComponent } from './bolnica/logs/code-logs/code-logs.component';

@NgModule({
  declarations: [
    AppComponent,
    AddCertificateComponent,
    LoginComponent,
    MainPageComponent,
    PatientsComponent,
    TypeLogsComponent,
    AllLogsComponent,
    CodeLogsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    CertificateService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
