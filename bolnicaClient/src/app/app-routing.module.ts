import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddCertificateComponent } from './bolnica/add-certificate/add-certificate.component';


const routes: Routes = [{
  path: '',
  component: AddCertificateComponent
}];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
