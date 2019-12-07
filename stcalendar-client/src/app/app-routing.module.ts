import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { from } from 'rxjs';
import { AppNavBarComponent } from './theme/layout/user/app-nav-bar/app-nav-bar.component';
import { UserComponent } from './theme/layout/user/user.component';
import { AuthComponent } from './theme/layout/auth/auth.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    children: [
      {
        path: '',
        redirectTo: 'user/home',
        pathMatch: 'full'
      }, {
        path: 'user',
        loadChildren: () => import('./home/home.module').then(module => module.HomeModule),
      } ,
    ]
  }, {
    path: '',
    component: AuthComponent,
    children: [
      {
        path: 'auth',
        loadChildren: () => import('./pages/authentication/authentication.module').then(module => module.AuthenticationModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
