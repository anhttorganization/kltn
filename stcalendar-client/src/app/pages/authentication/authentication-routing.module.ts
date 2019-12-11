import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [  {
  path: '',
  children: [
    {
      path: 'register',
      loadChildren: () => import('./register/register.module').then(module => module.RegisterModule)
    },
    {
      path: 'login',
      loadChildren: () => import('./login/login.module').then(module => module.LoginModule)
    },
    {
      path: 'confirm',
      loadChildren: () => import('./confirm/confirm.module').then(module => module.ConfirmModule)
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
