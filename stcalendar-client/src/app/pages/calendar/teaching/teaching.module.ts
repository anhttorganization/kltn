import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TeachingRoutingModule } from './teaching-routing.module';
import { TeachingComponent } from './teaching.component';


@NgModule({
  declarations: [TeachingComponent],
  imports: [
    CommonModule,
    TeachingRoutingModule
  ]
})
export class TeachingModule { }
