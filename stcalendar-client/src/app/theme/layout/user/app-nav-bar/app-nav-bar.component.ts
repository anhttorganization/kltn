import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { LoginServices } from '../../../../services/login.services.ts.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './app-nav-bar.component.html',
  styleUrls: ['./app-nav-bar.component.scss']
})
export class AppNavBarComponent implements OnInit {
  @Input() userInfo;


  constructor(
    private loginServices: LoginServices,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
  }

  public doLogout() {
    localStorage.removeItem('token');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('user');
    //reload
    this.router.navigate(['/'])
  .then(() => {
    window.location.reload();
  });
  }

}
