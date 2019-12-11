import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ShareMessageService } from 'src/app/services/share-message.service';

@Component({
  selector: 'app-confirm',
  templateUrl: './confirm.component.html',
  styleUrls: ['./confirm.component.scss']
})
export class ConfirmComponent implements OnInit {
  token;
  constructor(
    private aRoute: ActivatedRoute,
    private router: Router,
    private toastr: ToastrService,
    private  shareMessageService : ShareMessageService
  ) { }

  ngOnInit() {
    // this.toastr.show("Test",null,{
    //   disableTimeOut: true,
    //   tapToDismiss: false,
    //   toastClass: "toast toast-success",
    //   closeButton: true,
    //   positionClass:'bottom-right'
    // });
    // this.mytoast(this.token, 'toast-success');
    this.aRoute.paramMap.subscribe(params => {
      console.log(params.get('token'));
      this.token = params.get('token');
      this.router.navigate(['auth/login']);


      alert('haha');
    });


  }

  // mytoast(msg: string, status: string){
  //   this.toastr.show(msg,null,{
  //     disableTimeOut: true,
  //     tapToDismiss: true,
  //     toastClass: "toast "+status,
  //     closeButton: true,
  //     positionClass:'bottom-right'
  //   });
  // }

}
