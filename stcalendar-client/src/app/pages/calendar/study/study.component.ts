import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { CalendarService } from 'src/app/services/calendar.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-study',
  templateUrl: './study.component.html',
  styleUrls: ['./study.component.scss']
})
export class StudyComponent implements OnInit {
  semester: string;
  studentId: string;
  formdata: FormGroup;
  token: string;

  semesterList = [
    { key: '20192', value: 'Học kỳ 2 - Năm học 2019-2020' },
    { key: '20191', value: 'Học kỳ 1 - Năm học 2019-2020' },
    { key: '20183', value: 'Học kỳ 3 - Năm học 2018-2019' }
  ];

  constructor(
    private calendarService: CalendarService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.token = localStorage.getItem('token');

    this.calendarService.checkAuth(this.token).subscribe(res => {
        if (res) {
          window.location.href = res;
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

  ngOnInit() {
    this.semester = this.semesterList[0].key;
    this.formdata = new FormGroup({
      studentId: new FormControl(
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(5),
          Validators.maxLength(10)
        ])
      ),
      semester: new FormControl(
        this.semester,
        Validators.compose([Validators.required])
      )
    });
  }
  onClickSubmit(data) {
    if (
      this.formdata.controls.studentId.errors ||
      !this.formdata.controls.studentId.value.match('^\\d+$')
    ) {
      this.mytoast('Mã sinh viên không hợp lệ', 'error');
    } else if (this.formdata.controls.semester.errors) {
      this.mytoast('Bạn chưa chọn học kỳ', 'error');
    } else {
      const result = this.calendarService
        .layLichGV(data.studentId, data.semester, this.token)
        .subscribe(
          res => {
            console.log(res);
            if (res && res.message === 'CREATED') {
              this.mytoast('Thêm lịch thành công', 'success');
            } else if (res && res.message === 'EXISTED') {
              this.mytoast('Lịch đã tồn tại', 'success');
              // this.router.navigate(['auth/login']);
            } else {
              this.mytoast('Thêm lịch thất bại', 'error');
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
