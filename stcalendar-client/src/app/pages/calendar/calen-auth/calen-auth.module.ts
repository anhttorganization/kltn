import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalenAuthRoutingModule } from './calen-auth-routing.module';
import { CalenAuthComponent } from './calen-auth.component';


@NgModule({
  declarations: [CalenAuthComponent],
  imports: [
    CommonModule,
    CalenAuthRoutingModule
  ]
})
export class CalenAuthModule { }
