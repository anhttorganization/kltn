import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from 'src/ultils/ultis';
import { User } from '../user';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }
  getProfile(token: string): Observable<User> {
    const url = AppCommon.baseUrl + '/me';
    return this.http.get<User>(url, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + token)
    });
  }
  public changeProfile(user, token: string) {
    const url = AppCommon.baseUrl + '/users';
    const body = {firstName: user.firstName,
      lastName: user.lastName,
      faculty: user.faculty,
      clazz: user.clazz
    };

    return this.http.put(url, body, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + token),
      observe: 'response',
      responseType: 'json'
    });
  }
}
