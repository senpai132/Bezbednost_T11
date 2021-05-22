import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AllCertificatesComponent } from './admin/certificates/all-certificates/all-certificates.component';
import { CertificateRequestsComponent } from './admin/certificates/certificate-requests/certificate-requests.component';
import { RevokedCertificatesComponent } from './admin/certificates/revoked-certificates/revoked-certificates.component';
import { LoginComponent } from './admin/login/login.component';
import { AllUsersComponent } from './admin/all-users/all-users.component';
import { MainPageComponent } from './admin/main-page/main-page.component';
import { RolesGuard } from './guards/roles.guard';
import { LoginGuard } from './guards/login.guard';


const routes: Routes = [{
  path: '',
  component: LoginComponent,
  canActivate: [LoginGuard]
},
{
  path: 'main',
  component: MainPageComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'certificates',
  component: AllCertificatesComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'requests',
  component: CertificateRequestsComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'revoked',
  component: RevokedCertificatesComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'users',
  component: AllUsersComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
