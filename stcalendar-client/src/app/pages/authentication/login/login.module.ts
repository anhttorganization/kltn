import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './login.component';
import { FormsModule } from '@angular/forms';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
@NgModule({
  declarations: [LoginComponent],
  imports: [
    CommonModule,
    LoginRoutingModule,
    FormsModule, Ng4LoadingSpinnerModule.forRoot()
  ]
})
export class LoginModule { }
