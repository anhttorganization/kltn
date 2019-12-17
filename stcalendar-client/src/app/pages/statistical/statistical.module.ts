import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StatisticalRoutingModule } from './statistical-routing.module';
import { StatisticalComponent } from './statistical.component';


@NgModule({
  declarations: [StatisticalComponent],
  imports: [
    CommonModule,
    StatisticalRoutingModule
  ]
})
export class StatisticalModule { }
