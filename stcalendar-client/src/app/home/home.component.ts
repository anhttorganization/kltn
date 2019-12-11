import { Component, OnInit } from "@angular/core";

import { LoginServices } from "../services/login.services.ts.service";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import jwt_decode from "jwt-decode";
import { AuthInfoService } from "../services/auth-info.service";

@Component({
  selector: "app-home",
  templateUrl: "./home.component.html",
  styleUrls: ["./home.component.scss"]
})
export class HomeComponent implements OnInit {
  roleFromUser: string;

  constructor(private authInfoService: AuthInfoService) {}

  ngOnInit() {
    this.roleFromUser = this.authInfoService.roleFromUser;
    console.log(this.roleFromUser)
  }
}
