import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ImportRoutingModule } from './import-routing.module';
import { ImportComponent } from './import.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [ImportComponent],
  imports: [
    CommonModule,
    HttpClientModule,
    ImportRoutingModule, FormsModule, ReactiveFormsModule
  ]
})
export class ImportModule { }
