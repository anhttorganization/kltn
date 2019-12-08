import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from '../../ultils/ultis';

@Injectable({
  providedIn: 'root'
})
export class LoginServices {

  private baseUrl = AppCommon.baseUrl + '/auth/login';

  constructor(private http: HttpClient) { }

  public checkLogin(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}`, { username, password });
  }

}
