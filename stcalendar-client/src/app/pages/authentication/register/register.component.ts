import { UserRegister } from './../../../model/user-register.model';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { RegisterService } from 'src/app/services/register.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  constructor(
    private registerServices: RegisterService,
    private router: Router,
    private toastr: ToastrService
  ) {}
  username;
  formdata: FormGroup;
  selectedRole: string;
  roles = [
    { key: 'ROLE_OFFICER', value: 'Cán bộ' },
    { key: 'ROLE_TEACHER', value: 'Giảng viên' },
    { key: 'STUDENT', value: 'Sinh viên' }
  ];

  ngOnInit() {
    this.selectedRole = 'ROLE_OFFICER';
    this.formdata = new FormGroup({
      role: new FormControl(this.roles[0].key),
      username: new FormControl(''),
      password: new FormControl(''),
      repass: new FormControl(''),
      staffMail: new FormControl(''),
      lastName: new FormControl(''),
      firstName: new FormControl(''),
      faculty: new FormControl(''),
      clazz: new FormControl(''),
      avatar: new FormControl(''),
    });
  }

  onSelectRole(selected) {
    this.selectedRole = selected;
    console.log(selected);
  }

  onClickSubmit(data) {
    console.log(data);
    // let user = UserRegister;
    // user.username = data.username;
    // user.firstName = data.firstName;
    // user.lastName = data.lastName;
    // user.avatar: data.avatar;
    // user.faculty: data.faculty;
    // user.clazz: data.clazz;
    // user.password: data.password;
    // user.repass: data.repass;
    // user.role: data.role;
    // user.staffMail: data.staffMail;

   let result = this.registerServices.register(data);
    console.log(result);

  }

}
