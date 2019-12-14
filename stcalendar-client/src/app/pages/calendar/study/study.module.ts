import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { StudyRoutingModule } from './study-routing.module';
import { StudyComponent } from './study.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [StudyComponent],
  imports: [
    CommonModule,
    StudyRoutingModule, FormsModule, ReactiveFormsModule
  ]
})
export class StudyModule { }
