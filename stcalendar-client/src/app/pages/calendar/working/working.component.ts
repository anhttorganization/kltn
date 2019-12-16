import { WorkingEventVo } from './model/working-event-vo.model';
import { GoogleEvent } from './../model/google-event.model';
import { GoogleCalendar } from './../../../model/google-calendar.model';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CalendarService } from 'src/app/services/calendar.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { stringify } from 'querystring';

@Component({
  selector: 'app-working',
  templateUrl: './working.component.html',
  styleUrls: ['./working.component.scss']
})
export class WorkingComponent implements OnInit {
  token: string;
  list: any[];
  calendars: GoogleCalendar[];

  formdata: FormGroup;
  searchText: string;
  isChecked = false;
  selectedList = [];
  selectedCalendar: string;
  isShowList = false;
  events: WorkingEventVo[];

  constructor(
    private calendarService: CalendarService,
    private router: Router,
    private toastr: ToastrService
  ) {
    this.token = localStorage.getItem('token');

    this.calendarService.checkAuth(this.token).subscribe(
      res => {
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
    // Lay danh sach lich cong tac
    this.calendarService.getWorking(this.token).subscribe(
      res => {
        if (res) {
          console.log(res);
          res.map((data, idx) => {
            (data.index = idx), (data.isSelected = false);
          });
          this.list = res;
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

    // Lay danh sach lich
    this.calendarService.getCalendars(this.token).subscribe(
      data => {
        console.log('data: ');
        console.log(data);
        data.map(calen => new GoogleCalendar().deserializable(calen));
        this.calendars = data;
        console.log('calendar: ');
        console.log(this.calendars);
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

  updateSelectedList(event: any) {
    event.isSelected = !event.isSelected;
    console.log(event);
    if (event.isSelected) {
      this.selectedList.push(event);
    } else {
      this.selectedList = this.selectedList.filter(
        obj => obj.index !== event.index
      );
    }
    console.log(this.selectedList);
  }

  onSelectCalendar(event: any) {
    this.selectedCalendar = event;
    console.log(this.selectedCalendar);
  }

  onFocus() {
    this.isShowList = true;
  }

  onBlur() {
    this.isShowList = false;
  }

  onClickSubmit() {
    if (!this.selectedList.length) {
      this.mytoast('Không có sự kiện nào được chọn', 'error');
    } else if (!this.selectedCalendar) {
      this.mytoast('Không có calendar nào được chọn', 'error');
    } else {
      this.events = this.selectedList.map(c => new WorkingEventVo().deserialize(c));

      const result = this.calendarService
        .insertWorking( this.token, this.selectedCalendar, this.events)
        .subscribe(
          res => {
            console.log(res);
            if (res && res.ok) {
              this.mytoast('Thêm lịch thành công', 'success');
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
    this.toastr.show(msg, null, {
      // disableTimeOut: true,
      tapToDismiss: true,
      toastClass: 'toast toast-' + status,
      closeButton: true,
      positionClass: 'toast-bottom-right',
      timeOut: 5000
    });
  }
}
