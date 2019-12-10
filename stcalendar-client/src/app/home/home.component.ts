import { Component, OnInit } from '@angular/core';

import { LoginServices } from '../services/login.services.ts.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  userInfo: object;
  token: string;
  refreshToken: string;

  constructor(
    private loginServices: LoginServices,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.token = localStorage.getItem('token');
    const payload = jwt_decode(this.token);
    const isExp =  loginServices.isTokenExpired(payload.exp);



  }

  ngOnInit(



  ) {
  }

}
