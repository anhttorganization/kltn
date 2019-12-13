import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { CalendarService } from 'src/app/services/calendar.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-working',
  templateUrl: './working.component.html',
  styleUrls: ['./working.component.scss']
})
export class WorkingComponent implements OnInit {
  formdata: FormGroup;
  token: string;
  list: string;

  searchText;
  constructor(
    private calendarService: CalendarService,
    private router: Router,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.token = localStorage.getItem('token');

    this.calendarService.getWorking(this.token).subscribe(res => {
      if (res) {
        this.list = res;
        console.log(this.list);
      }
  },
  err => {
    console.log(err);
    let msg = err.error.message;
    if (!msg) {
      msg = 'Có lỗi xảy ra';
    }
    this.mytoast(msg, 'error');
  }
);
  }

  onClickSubmit(data) {
    if (this.formdata.controls.studentId.errors || !this.formdata.controls.studentId.value.trim().toLowerCase().match('^[a-z]{3}\\d{2}$')) {
      this.mytoast('Mã giảng viên không hợp lệ', 'error');
    } else if (this.formdata.controls.semester.errors) {
      this.mytoast('Bạn chưa chọn học kỳ', 'error');
    } else {
      const result = this.calendarService.layLichGV(data.studentId, data.semester, this.token).subscribe(res => {
        console.log(res);
        if (res && res.message === 'CREATED') {
        this.mytoast('Thêm lịch thành công', 'success');
        } else if (res && res.message === 'EXISTED') {
          this.mytoast('Lịch đã tồn tại', 'success');
          // this.router.navigate(['auth/login']);
        } else {
          this.mytoast('Thêm lịch thất bại', 'error');
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
