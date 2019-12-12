import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, Params } from '@angular/router';
import { CalendarService } from 'src/app/services/calendar.service';
import { ToastrService } from 'ngx-toastr';
import { data } from 'jquery';

@Component({
  selector: 'app-calen-auth',
  templateUrl: './calen-auth.component.html',
  styleUrls: ['./calen-auth.component.scss']
})
export class CalenAuthComponent implements OnInit {
  token: string;
  constructor(
    private activatedRoute: ActivatedRoute,
    private calendarService: CalendarService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.token = localStorage.getItem('token');

    this.activatedRoute.queryParams.subscribe(params => {
      let code = params.code;
      console.log(code); // Print the parameter to the console.

      if(code){
        this.calendarService.getAuth( this.token, code).subscribe(res => {
          if(res){
            console.log(res);
            this.mytoast("Cấp quyền thành công cho ứng dụng", 'success');
            // alert("cấp quyền thành công")
            this.router.navigate([''])
          }
        }, err => {
              console.log(err);
              let msg = err.error.message;
              if (!msg) {
                msg = 'Có lỗi xảy ra';
              }
              this.mytoast(msg, 'error');
          });
      }
  });

  //   const result = this.calendarService.getAuth( this.token).subscribe(res => {
  //     console.log(res);
  //     if (res && res.message === 'CREATED') {
  //     this.mytoast('Thêm lịch thành công', 'success');
  //     } else if (res && res.message === 'EXISTED') {
  //       this.mytoast('Lịch đã tồn tại', 'success');
  //       // this.router.navigate(['auth/login']);
  //     } else {
  //       this.mytoast('Thêm lịch thất bại', 'error');
  //     }

  //   }, err => {
  //     console.log(err);
  //     let msg = err.error.message;
  //     if (!msg) {
  //       msg = 'Có lỗi xảy ra';
  //     }
  //     this.mytoast(msg, 'error');
  // });

  }

  ngOnInit() {}



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
