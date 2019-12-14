import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  // Thông tin người dùng
  hoDemND: string = "Đặng Quốc";
  tenND: string = "Thắng";
  khoaND: string = "Công nghệ thông tin";
  lopND: string = "K62CNPM";


  constructor() {
    
  }

  ngOnInit() {
  }

  @ViewChild('inputHoDem', { static: false }) inputHoDem: ElementRef;
  @ViewChild('inputTen', { static: false }) inputTen: ElementRef;
  @ViewChild('inputLop', { static: false }) inputLop: ElementRef;
  @ViewChild('inputKhoa', { static: false }) inputKhoa: ElementRef;

  

  isEditingHoDem = false; // Đang không ở chế độ chỉnh sửa
  isEditingTen = false; // Đang không ở chế độ chỉnh sửa
  isEditingLop = false; // Đang không ở chế độ chỉnh sửa
  isEditingKhoa = false; // Đang không ở chế độ chỉnh sửa
  actionHoDem:string = "Chỉnh sửa";
  actionTen:string = "Chỉnh sửa";
  actionLop:string = "Chỉnh sửa";
  actionKhoa:string = "Chỉnh sửa";

  editProfile(field,value){
      //Cập nhật thông tin
      switch (field) {
        case "hodem-field":
          if (this.isEditingHoDem){
            this.isEditingHoDem = !this.isEditingHoDem;
            this.hoDemND = value;
            this.actionHoDem = "Chỉnh sửa";
          }else{
            this.isEditingHoDem = !this.isEditingHoDem;
            this.actionHoDem = "Cập nhật";
            setTimeout(() => {
              this.inputHoDem.nativeElement.focus();
            }, 0);
          }
          break;
        case "ten-field":
          if (this.isEditingTen){
            this.isEditingTen = !this.isEditingTen;
            this.tenND = value;
            this.actionTen = "Chỉnh sửa";
          }else{
            this.isEditingTen = !this.isEditingTen;
            this.actionTen = "Cập nhật";
            setTimeout(() => {
              this.inputTen.nativeElement.focus();
            }, 0);
          }
          break;
        case "khoa-field":
          if (this.isEditingKhoa){
            this.isEditingKhoa = !this.isEditingKhoa;
            this.khoaND = value;
            this.actionKhoa = "Chỉnh sửa";
          }else{
            this.isEditingKhoa = !this.isEditingKhoa;
            this.actionKhoa = "Cập nhật";
            setTimeout(() => {
              this.inputKhoa.nativeElement.focus();
            }, 0);
          }
          break;
        case "lop-field":
          if (this.isEditingLop){
            this.isEditingLop = !this.isEditingLop;
            this.lopND = value;
            this.actionLop = "Chỉnh sửa";
          }else{
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

  bottomAffectHoDem(){
    let styles = {
      "width": this.isEditingHoDem ? "100%" : "",
    }
    return styles;
  }

  bottomAffectTen() {
    let styles = {
      "width": this.isEditingTen ? "100%" : "",
    }
    return styles;
  }
  bottomAffectLop() {
    let styles = {
      "width": this.isEditingLop ? "100%" : "",
    }
    return styles;
  }
  bottomAffectKhoa() {
    let styles = {
      "width": this.isEditingKhoa ? "100%" : "",
    }
    return styles;
  }

}
