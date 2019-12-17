import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WorkingRoutingModule } from './working-routing.module';
import { WorkingComponent } from './working.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Ng4LoadingSpinnerModule } from 'ng4-loading-spinner';
import {FilterPipe } from './filter.pipe';

@NgModule({
  declarations: [WorkingComponent, FilterPipe],
  imports: [
    CommonModule,
    WorkingRoutingModule,FormsModule, ReactiveFormsModule, Ng4LoadingSpinnerModule
  ]
})
export class WorkingModule { }
