import { Injectable } from '@angular/core';
import {
  HttpClient,
  HttpHeaders,
  HttpErrorResponse,
  HttpParams
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppCommon } from '../../ultils/ultis';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {
  constructor(private http: HttpClient) {}

  public layLichGV(studentId: string, semester: string, token: string): Observable<any> {
    const url = AppCommon.baseUrl + '/calendar/schedule/create';
    const body = {
      studentId,
      semester
    };

    return this.http.post(url, body, {
      headers: new HttpHeaders({
        'Content-Type':  'application/json;charset=UTF-8',
        'Authorization': 'Bearer ' + token
      }),
      responseType: 'json'
    });
  }
}
