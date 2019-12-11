import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { WorkingRoutingModule } from './working-routing.module';
import { WorkingComponent } from './working.component';


@NgModule({
  declarations: [WorkingComponent],
  imports: [
    CommonModule,
    WorkingRoutingModule
  ]
})
export class WorkingModule { }
