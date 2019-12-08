import { TestBed } from '@angular/core/testing';

import { LoginServices } from './login.services.ts.service';

describe('Login.Services.TsService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service:LoginServices = TestBed.get(LoginServices);
    expect(service).toBeTruthy();
  });
});
