import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';


const routes: Routes = [{
  path: '',
  component: AllCertificatesComponent
},
{
  path: 'requests',
  component: CertificateRequestsComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
