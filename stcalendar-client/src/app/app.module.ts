import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AdminComponent } from './theme/layout/admin/admin.component';
import { UserComponent } from './theme/layout/user/user.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';
import { AppNavBarComponent } from './theme/layout/user/app-nav-bar/app-nav-bar.component';
import { BreadscrumbComponent } from './theme/shared/components/breadscrumb/breadscrumb.component';
import { AuthComponent } from './theme/layout/auth/auth.component';

@NgModule({
  declarations: [
    AppComponent,
    AdminComponent,
    UserComponent,
    AppNavBarComponent,
    BreadscrumbComponent,
    AuthComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
