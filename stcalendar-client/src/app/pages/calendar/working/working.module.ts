import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WorkingRoutingModule } from './working-routing.module';
import { WorkingComponent } from './working.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import {FilterPipe } from './filter.pipe';

@NgModule({
  declarations: [WorkingComponent, FilterPipe],
  imports: [
    CommonModule,
    WorkingRoutingModule,FormsModule, ReactiveFormsModule
  ]
})
export class WorkingModule { }
