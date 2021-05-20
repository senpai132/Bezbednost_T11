import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';
import { RevokedCertificatesComponent } from './admin/certificates/revoked-certificates/revoked-certificates.component';
import { LoginComponent } from './admin/login/login.component';
import { AllUsersComponent } from './admin/all-users/all-users.component';
import { MainPageComponent } from './admin/main-page/main-page.component';


const routes: Routes = [{
  path: '',
  component: LoginComponent
},
{
  path: 'main',
  component: MainPageComponent
},
{
  path: 'certificates',
  component: AllCertificatesComponent
},
{
  path: 'requests',
  component: CertificateRequestsComponent
},
{
  path: 'revoked',
  component: RevokedCertificatesComponent
},
{
  path: 'users',
  component: AllUsersComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
