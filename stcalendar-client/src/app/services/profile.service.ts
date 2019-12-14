import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from 'src/ultils/ultis';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private http: HttpClient) { }
  public getProfile(): Observable<any> {
    const url = AppCommon.baseUrl + '/api/me';
    return this.http.get(url, {    });
  }
}
