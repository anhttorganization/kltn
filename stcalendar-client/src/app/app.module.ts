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
import { LoginServices } from './services/login.services.ts.service';
import { HttpClientModule } from '@angular/common/http';
import { ToastrModule } from 'ngx-toastr';
import { HashLocationStrategy, Location, LocationStrategy, PathLocationStrategy } from '@angular/common';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';

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
    MaterialModule,
    HttpClientModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-bottom-right'
    }),
    Ng4LoadingSpinnerModule.forRoot()
  ],
  providers: [LoginServices,
    // Location,
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
