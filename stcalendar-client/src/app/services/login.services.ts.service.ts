import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from '../../ultils/ultis';

@Injectable({
  providedIn: 'root'
})
export class LoginServices {

  private baseUrl = AppCommon.baseUrl + '/auth/login';

  constructor(private http: HttpClient) { }

  public login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}`, { username, password });
  }

  public getMe(token: string): Observable<any> {
    return this.http.get(`${this.baseUrl}`,
    {
      headers: new HttpHeaders().set('Authorization', 'Bearer ' + token),
    });
  }

  public refreshToken() {

  }

  public isTokenExpired(exp) {
    if (Date.now() >= exp * 1000) {
      return true;
    }
  }


}
