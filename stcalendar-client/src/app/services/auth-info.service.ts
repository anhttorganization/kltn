import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthInfoService {
  private _roleFromUser: string;

  get roleFromUser(): string {
    return this._roleFromUser;
  }

  setroleFromUser(role: string) {
    this._roleFromUser = role;
  }

  constructor() { }
}
