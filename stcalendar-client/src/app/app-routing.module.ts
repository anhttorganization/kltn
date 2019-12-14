import { RegisterComponent } from './pages/authentication/register/register.component';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { from } from 'rxjs';
import { AppNavBarComponent } from './theme/layout/user/app-nav-bar/app-nav-bar.component';
import { UserComponent } from './theme/layout/user/user.component';
import { AuthComponent } from './theme/layout/auth/auth.component';
import { AdminComponent } from './theme/layout/admin/admin.component';

const routes: Routes = [
  {
    path: '',
    component: UserComponent,
    children: [
      {
        path: '',
        redirectTo: '/user/home',
        pathMatch: 'full'
      }, {
        path: 'user',
        loadChildren: () => import('./home/home.module').then(module => module.HomeModule),
      }, {
        path: 'profile',
        loadChildren: () => import('./pages/profile/profile.module').then(module => module.ProfileModule),
      }, {
        path: 'calendar',
        loadChildren: () => import('./pages/calendar/calendar.module').then(module => module.CalendarModule),
      }
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
  }, {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'admin',
        redirectTo: 'admin/dashboard',
        pathMatch: 'full'
      }, {
        path: 'admin',
        loadChildren: () => import('./dashboard/dashboard.module').then(module => module.DashboardModule),
      } ,
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
