import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BreadscrumbComponent } from './breadscrumb.component';

describe('BreadscrumbComponent', () => {
  let component: BreadscrumbComponent;
  let fixture: ComponentFixture<BreadscrumbComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BreadscrumbComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BreadscrumbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
