import { Component, OnInit } from '@angular/core';
import { LoginServices } from '../../../services/login.services.ts.service';
// import { resolveSanitizationFn } from '@angular/compiler/src/render3/view/template';
import jwt_decode from 'jwt-decode';
import { Router } from '@angular/router';
import { AppCommon } from '../../../../ultils/ultis';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
   username: string;
   password: string;

  constructor(
    private loginServices: LoginServices,
    private router: Router,
    private toastr: ToastrService
  ) {}

  ngOnInit() {}

  doLogin() {
    this.loginServices
      .login(this.username, this.password)
      .subscribe(res => {
        if (res && res.token) {
          // decode
          const payload = jwt_decode(res.token);
          console.log(payload);
          switch (payload.scopes[0]) {
            case AppCommon.ROLE_ADMIN:
              // save token
              localStorage.setItem('token', res.token);
              localStorage.setItem('refreshToken', res.refreshToken);
              localStorage.setItem('user', JSON.stringify( payload.user));

              this.router.navigate(['admin']);
              this.toastr.success('Đăng nhập thành công!', 'Success');
              break;
            case AppCommon.ROLE_USER:
            case AppCommon.ROLE_STUDENT:
            case AppCommon.ROLE_TEACHER:
            case AppCommon.ROLE_OFFICER:
              // save token
              localStorage.setItem('token', res.token);
              localStorage.setItem('refreshToken', res.refreshToken);
              localStorage.setItem('user', JSON.stringify( payload.user));


              this.router.navigate(['']);
              this.toastr.success('Đăng nhập thành công!', 'Success');
              break;
            default:
              this.toastr.success('Đăng nhập không thành công!', 'Error');
          }

        }
      });
  }



}
