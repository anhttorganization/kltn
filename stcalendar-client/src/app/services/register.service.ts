import { UserRegister } from './../model/user-register.model';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from '../../ultils/ultis';
import { analyzeAndValidateNgModules } from '@angular/compiler';


@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor(private http: HttpClient) { }

  // public login(username: string, password: string): Observable<any> {
  //   return this.http.post(`${this.baseUrl}`, { username, password });
  // }

  public register(user) {
    const url = AppCommon.baseUrl + '/auth/register';
    const body = {username: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      avatar: user.avatar,
      faculty: user.faculty,
      clazz: user.clazz,
      password: user.password,
      repass: user.repass,
      role: user.role,
      staffMail: user.staffMail
    };

    return this.http.post(url, body, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      observe: 'response', // to display the full response
      responseType: 'json'
    }).subscribe(response => {
      console.log(response);
      return response;
  }, err => {
      throw err;
  });
}
}
