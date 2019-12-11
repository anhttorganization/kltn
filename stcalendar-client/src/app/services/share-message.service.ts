import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ShareMessageService {

  constructor() { }


  private _msgFromRegisterConfirm: string;

  get msgFromRegisterConfirm(): string {
    return this._msgFromRegisterConfirm;
  }

  setmsgFromRegisterConfirm(msg: string) {
    this._msgFromRegisterConfirm = msg;
  }
}
