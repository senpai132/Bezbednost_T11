import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';
import { RevokedCertificatesComponent } from './admin/certificates/revoked-certificates/revoked-certificates.component';
import { LoginComponent } from './admin/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AllUsersComponent } from './admin/all-users/all-users.component';

@NgModule({
  declarations: [
    AppComponent,
    AllCertificatesComponent,
    CertificateRequestsComponent,
    RevokedCertificatesComponent,
    LoginComponent,
    AllUsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
