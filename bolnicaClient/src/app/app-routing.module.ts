import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCertificateComponent } from './bolnica/add-certificate/add-certificate.component';
import { LoginComponent } from './bolnica/login/login.component';
import { AllLogsComponent } from './bolnica/logs/all-logs/all-logs.component';
import { CodeLogsComponent } from './bolnica/logs/code-logs/code-logs.component';
import { TypeLogsComponent } from './bolnica/logs/type-logs/type-logs.component';
import { MainPageComponent } from './bolnica/main-page/main-page.component';
import { PatientsComponent } from './bolnica/patients/patients.component';
import { LoginGuard } from './guards/login.guard';
import { RolesGuard } from './guards/roles.guard';


const routes: Routes = [{
  path: '',
  component: LoginComponent,
  canActivate: [LoginGuard]
},
{
  path: 'main',
  component: MainPageComponent
},
{
  path: 'add_certificate',
  component: AddCertificateComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'patients',
  component: PatientsComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_DOCTOR' }
},
{
  path: 'type_logs',
  component: TypeLogsComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_DOCTOR' }
},
{
  path: 'all_logs',
  component: AllLogsComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
},
{
  path: 'code_logs',
  component: CodeLogsComponent,
  canActivate: [RolesGuard],
  data: { expectedRoles: 'ROLE_ADMIN' }
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
