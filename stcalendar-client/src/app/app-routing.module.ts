import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { from } from 'rxjs';
import { AppNavBarComponent } from './theme/layout/user/app-nav-bar/app-nav-bar.component';
import { UserComponent } from './theme/layout/user/user.component';

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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
