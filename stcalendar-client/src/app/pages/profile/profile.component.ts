import { Component, OnInit, ViewChild, ElementRef } from "@angular/core";

import { Observable } from "rxjs";
import { FormGroup } from "@angular/forms";
import { data } from "jquery";
import { ProfileService } from "src/app/services/profile.service";
import { UserProfile } from '../../model/user-profile.model';


@Component({
  selector: "app-profile",
  templateUrl: "./profile.component.html",
  styleUrls: ["./profile.component.scss"]
})
export class ProfileComponent implements OnInit {
  [x: string]: any;

  // Thông tin người dùng
  token: string;
  user: UserProfile;
  constructor(private profileService: ProfileService) {}
  @ViewChild("inputHoDem", { static: false }) inputHoDem: ElementRef;
  @ViewChild("inputTen", { static: false }) inputTen: ElementRef;
  @ViewChild("inputLop", { static: false }) inputLop: ElementRef;
  @ViewChild("inputKhoa", { static: false }) inputKhoa: ElementRef;

  isEditingHoDem = false; // Đang không ở chế độ chỉnh sửa
  isEditingTen = false; // Đang không ở chế độ chỉnh sửa
  isEditingLop = false; // Đang không ở chế độ chỉnh sửa
  isEditingKhoa = false; // Đang không ở chế độ chỉnh sửa
  actionHoDem = "Chỉnh sửa";
  actionTen = "Chỉnh sửa";
  actionLop = "Chỉnh sửa";
  actionKhoa = "Chỉnh sửa";

  ngOnInit() {
    this.reloadData();
  }
  reloadData() {
    this.token = localStorage.getItem("token");
    this.profileService.getProfile(this.token).subscribe(
      data => {
        if (data) {
          this.user = data;
        }
      },
      errror => {
        console.log(errror);
      }
    );
  }
  editProfile(field, value) {
    // Cập nhật thông tin
    switch (field) {
      case "hodem-field":
        if (this.isEditingHoDem) {
          this.isEditingHoDem = !this.isEditingHoDem;
          this.actionHoDem = "Chỉnh sửa";
          this.user.lastName = value;
          console.log("hello" + this.user);
          this.token = localStorage.getItem('token');
          this.profileService.changeProfile(this.user, this.token).subscribe(
            (data) => {
              console.log("hello 2222"+ data);
              this.reloadData();
            },
            error => console.log(error));
          console.log("hello 3333" + this.user);
        } else {
          this.isEditingHoDem = !this.isEditingHoDem;
          this.actionHoDem = "Cập nhật";
          setTimeout(() => {
            this.inputHoDem.nativeElement.focus();
          }, 0);
        }
        break;
      case "ten-field":
        if (this.isEditingTen) {
          this.isEditingTen = !this.isEditingTen;
          this.actionTen = "Chỉnh sửa";
          this.user.firstName = value;
          console.log(this.user);
          this.token = localStorage.getItem('token');
          console.log(this.token);
          this.profileService.changeProfile(this.user, this.token);
        } else {
          this.isEditingTen = !this.isEditingTen;
          this.actionTen = "Cập nhật";
          setTimeout(() => {
            this.inputTen.nativeElement.focus();
          }, 0);
        }
        break;
      case "khoa-field":
        if (this.isEditingKhoa) {
          this.isEditingKhoa = !this.isEditingKhoa;
          this.actionKhoa = "Chỉnh sửa";
          this.user.faculty = value;
        } else {
          this.isEditingKhoa = !this.isEditingKhoa;
          this.actionKhoa = "Cập nhật";
          setTimeout(() => {
            this.inputKhoa.nativeElement.focus();
          }, 0);
        }
        break;
      case "lop-field":
        if (this.isEditingLop) {
          this.isEditingLop = !this.isEditingLop;
          this.actionLop = "Chỉnh sửa";
          this.user.clazz = value;
        } else {
          this.isEditingLop = !this.isEditingLop;
          this.actionLop = "Cập nhật";
          setTimeout(() => {
            this.inputLop.nativeElement.focus();
          }, 0);
        }
        break;

      default:
        break;
    }
  }

  bottomAffectHoDem() {
    const styles = {
      width: this.isEditingHoDem ? "100%" : ""
    };
    return styles;
  }

  bottomAffectTen() {
    const styles = {
      width: this.isEditingTen ? "100%" : ""
    };
    return styles;
  }
  bottomAffectLop() {
    const styles = {
      width: this.isEditingLop ? "100%" : ""
    };
    return styles;
  }
  bottomAffectKhoa() {
    const styles = {
      width: this.isEditingKhoa ? "100%" : ""
    };
    return styles;
  }
  mytoast(msg: string, status: string) {
    this.toastr.show(msg, null, {
      // disableTimeOut: true,
      tapToDismiss: true,
      toastClass: "toast toast-" + status,
      closeButton: true,
      positionClass: "toast-bottom-right",
      timeOut: 5000
    });
  }
}
