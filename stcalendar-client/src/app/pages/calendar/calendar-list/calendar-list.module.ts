import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CalendarListRoutingModule } from './calendar-list-routing.module';
import { CalendarListComponent } from './calendar-list.component';


@NgModule({
  declarations: [CalendarListComponent],
  imports: [
    CommonModule,
    CalendarListRoutingModule
  ]
})
export class CalendarListModule { }
