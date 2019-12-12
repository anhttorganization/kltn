import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeachingRoutingModule } from './teaching-routing.module';
import { TeachingComponent } from './teaching.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [TeachingComponent],
  imports: [
    CommonModule,
    TeachingRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class TeachingModule { }
