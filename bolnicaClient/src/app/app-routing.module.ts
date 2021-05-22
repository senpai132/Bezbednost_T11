import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCertificateComponent } from './bolnica/add-certificate/add-certificate.component';
import { LoginComponent } from './bolnica/login/login.component';
import { MainPageComponent } from './bolnica/main-page/main-page.component';
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
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
