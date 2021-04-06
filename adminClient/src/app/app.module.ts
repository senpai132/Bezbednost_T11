import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { GenTableComponent } from './admin/gen-table/gen-table.component';
import { PaginationComponent } from './admin/pagination/pagination.component';
import { SafeHtmlPipe } from './pipes/safe-html-pipe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    CertificateRequestsComponent,
    AllCertificatesComponent,
    GenTableComponent,
    PaginationComponent,
    SafeHtmlPipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
