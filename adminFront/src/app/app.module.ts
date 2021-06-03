import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';
import { RevokedCertificatesComponent } from './admin/certificates/revoked-certificates/revoked-certificates.component';
import { LoginComponent } from './admin/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AllUsersComponent } from './admin/all-users/all-users.component';
import { MainPageComponent } from './admin/main-page/main-page.component';
import { SafeHtmlPipePipe } from './pipes/safe-html-pipe.pipe';
import { TokenInterceptorService } from './admin/services/token-interceptor.service';
import { CertificateService } from './admin/services/certificate.service';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    AllCertificatesComponent,
    CertificateRequestsComponent,
    RevokedCertificatesComponent,
    LoginComponent,
    AllUsersComponent,
    MainPageComponent,
    SafeHtmlPipePipe
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
