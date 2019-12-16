import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from 'src/ultils/ultis';
import { UserProfile } from '../model/user-profile.model';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }

  public getProfile(token: string): Observable<UserProfile> {
    const url = AppCommon.baseUrl + '/me';
    return this.http.get<UserProfile>(url, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + token)
    });
  }
  public changeProfile(user, token: string) {
    const url = AppCommon.baseUrl + '/api/users/' + user.id;
    const body = {username: user.username,
      firstName: user.firstName,
      lastName: user.lastName,
      avatar: user.avatar,
      faculty: user.faculty,
      clazz: user.clazz,
      password: user.password,
      repass: user.repass,
      enabled: user.enabled,
      ggRefreshToken: user.ggRefreshToken,
      createdAt: user.createdAt,
      updatedAt: user.updatedAt
    };
    return this.http.post(url, body, {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + token),
      observe: 'response',
      responseType: 'json'
      });
  }
}
