import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AppCommon } from 'src/ultils/ultis';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImportExcelService {

  constructor(private http: HttpClient) { }
  private baseUrl = AppCommon.baseUrl + '/dowload';

  public getFile(): Observable<any> {
    return this.http.get(`${this.baseUrl}`);
  }
}
