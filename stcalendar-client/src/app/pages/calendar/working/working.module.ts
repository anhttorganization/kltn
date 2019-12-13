import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WorkingRoutingModule } from './working-routing.module';
import { WorkingComponent } from './working.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [WorkingComponent],
  imports: [
    CommonModule,
    WorkingRoutingModule,FormsModule, ReactiveFormsModule
  ]
})
export class WorkingModule { }
