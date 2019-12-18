import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-import',
  templateUrl: './import.component.html',
  styleUrls: ['./import.component.scss'],
})
export class ImportComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  
  fileName: string = "Choose File";
  
  inputExcelChanged(event) {
    if (event.target.files.length > 0) {
      this.fileName = event.target.files[0].name;
    }
}

}
