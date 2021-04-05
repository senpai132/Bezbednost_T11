import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertificateRequestsComponent } from './admin/certificate-requests/certificate-requests.component';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';

@NgModule({
  declarations: [
    AppComponent,
    CertificateRequestsComponent,
    AllCertificatesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
