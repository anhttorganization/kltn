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
    { key: 'ROLE_STUDENT', value: 'Sinh viên' }
  ];

  ngOnInit() {
    this.selectedRole = 'ROLE_OFFICER';
    this.formdata = new FormGroup({
      role: new FormControl(this.roles[0].key),
      username: new FormControl('', Validators.compose([
        Validators.required,
        Validators.email
    ])),
      password: new FormControl('', Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(20)
    ])),
      repass: new FormControl('', Validators.compose([
        Validators.required,
        Validators.minLength(6),
        Validators.maxLength(20)
    ])),
      staffMail: new FormControl('', Validators.compose([
        Validators.required
    ])),
      lastName: new FormControl('', Validators.compose([
        Validators.required
    ])),
      firstName: new FormControl('', Validators.compose([
        Validators.required
    ])),
      faculty: new FormControl('', Validators.compose([
        Validators.required,
        Validators.minLength(3)
    ])),
      clazz: new FormControl('', Validators.compose([
        Validators.required,
        Validators.minLength(3)
    ])),
      avatar: new FormControl('')
    });
  }

  onSelectRole(selected) {
    this.selectedRole = selected;
    console.log(selected);
  }

  onClickSubmit(data) {
    // console.log(data);
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

    if (this.formdata.controls.username.errors) {
      this.mytoast('Tên tài khoản không đúng định dạng gmail', 'error');
    } else if (this.formdata.controls.lastName.errors) {
      this.mytoast('Họ đệm người dùng không hợp lệ', 'error');
    } else if (this.formdata.controls.firstName.errors) {
      this.mytoast('Tên người dùng không hợp lệ', 'error');
    } else if (this.formdata.controls.faculty.errors) {
      this.mytoast('Khoa/Đơn vị không hợp lệ', 'error');
    } else if (this.formdata.controls.clazz.errors) {
      this.mytoast('Tổ/bộ môn/lớp không hợp lệ', 'error');
    } else if (this.formdata.controls.password.errors) {
      this.mytoast('Mật khẩu không hợp lệ', 'error');
    } else if (this.formdata.controls.repass.errors) {
      this.mytoast('Mật khẩu nhập lại không hợp lệ', 'error');
    // tslint:disable-next-line: max-line-length
    } else if ((this.selectedRole === 'ROLE_OFFICER' || this.selectedRole === 'ROLE_TEACHER') && ( this.formdata.controls.staffMail.errors || !this.formdata.controls.staffMail.value.match('^\\w+@vnua.edu.vn$')  )) {
      this.mytoast('Email cán bộ không hợp lệ', 'error');
    } else if (this.formdata.controls.password.value !== this.formdata.controls.repass.value) {
      this.mytoast('Mật khẩu không trùng khớp', 'error');
    } else {
      this.registerServices.register(data).subscribe(res => {
        console.log(res);
        if (res && res.status == 201) {
        this.mytoast('Tài khoản đã tồn tại', 'error');
        } else if (res && res.body) {
          this.mytoast('Đăng ký thành công', 'success');
          this.router.navigate(['auth/login']);
        } else {
          this.mytoast('Đăng ký thất bại', 'error');
        }

      });

    }

  }

  mytoast(msg: string, status: string) {
    this.toastr.show(msg,null,{
      // disableTimeOut: true,
      tapToDismiss: true,
      toastClass: "toast toast-"+status,
      closeButton: true,
      positionClass:'toast-bottom-right',
      timeOut: 5000,

    });
  }
}
