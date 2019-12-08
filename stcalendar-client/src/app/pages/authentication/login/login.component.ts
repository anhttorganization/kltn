import { Component, OnInit } from '@angular/core';
import { LoginServices } from '../../../services/login.services.ts.service';
import { resolveSanitizationFn } from '@angular/compiler/src/render3/view/template';
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

  private username: string;
  private password: string;

  constructor(
    private loginServices: LoginServices,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {

  }

  doLogin() {
    this.loginServices.checkLogin(this.username, this.password).subscribe(res => {
      if (res && res.token) {
        // decode
        var payload = jwt_decode(res.token);

        // check role
        if (payload.scopes[0] === AppCommon.ROLE_ADMIN) {
          this.router.navigate(['admin']);
          this.toastr.success("Đăng nhập thành công!", "Success");
        }
        if (payload.scopes[0] === AppCommon.ROLE_USER) {
          this.router.navigate(['user']);
          this.toastr.success("Đăng nhập thành công!", "Success");
        }
      } else {
        this.toastr.success("Đăng nhập thất bại!", "Error");
      }
    });
  }
}
