import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WorkingComponent } from './working.component';


const routes: Routes = [{
  path: '',
  component: WorkingComponent
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkingRoutingModule { }
