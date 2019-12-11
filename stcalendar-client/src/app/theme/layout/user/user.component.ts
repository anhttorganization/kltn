import { Component, OnInit } from '@angular/core';
import { LoginServices } from '../../../services/login.services.ts.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import jwt_decode from 'jwt-decode';
import { AuthInfoService } from 'src/app/services/auth-info.service';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  userInfo: any;
  token: string;
  refreshToken: string;

  constructor(
    private loginServices: LoginServices,
    private router: Router,
    private toastr: ToastrService,
    private authInfoService: AuthInfoService
  ) {

    this.token = localStorage.getItem('token');
    if(this.token){
      const payload = jwt_decode(this.token);

      const isExp =  loginServices.isTokenExpired(payload.exp);
      // if(isExp){
      //     //refresh
      // }
      if(payload){
        this.userInfo = payload.user;
      }


      this.authInfoService.setroleFromUser(this.userInfo.role);
    }



  }

  ngOnInit() {
  }

}
