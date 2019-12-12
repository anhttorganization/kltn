import { CalendarListModule } from './calendar-list/calendar-list.module';
import { CalendarListComponent } from './calendar-list/calendar-list.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [{
  path: '',
  children: [
    {
      path: 'list',
      loadChildren: () => import('./calendar-list/calendar-list.module').then(module => module.CalendarListModule)
    },
    {
      path: 'working',
      loadChildren: () => import('./working/working.module').then(module => module.WorkingModule)
    },
    {
      path: 'teaching',
      loadChildren: () => import('./teaching/teaching.module').then(module => module.TeachingModule)
    },
    {
      path: 'study',
      loadChildren: () => import('./study/study.module').then(module => module.StudyModule)
    },
    {
      path: 'import',
      loadChildren: () => import('./import/import.module').then(module => module.ImportModule)
    },
    {
      path: 'test',
      loadChildren: () => import('./test/test.module').then(module => module.TestModule)
    },{
      path: 'auth',
      loadChildren: () => import('./calen-auth/calen-auth.module').then(module => module.CalenAuthModule)
    }
  ]
}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CalendarRoutingModule { }
