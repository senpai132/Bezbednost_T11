import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { LogEntry } from '../../model/log-entry';
import { LoggerService } from '../../services/logger.service';

@Component({
  selector: 'app-all-logs',
  templateUrl: './all-logs.component.html',
  styleUrls: ['./all-logs.component.sass']
})
export class AllLogsComponent implements OnInit {

  logs: LogEntry[];

  codeSearch: string;
  startDate: Date;
  endDate: Date;

  constructor(
    private toastr: ToastrService,
    private loggerService: LoggerService
  ) {
    this.codeSearch = "OVERVALUE";
   }

  ngOnInit(): void {
    this.loggerService.getAll().subscribe(res => {
      this.logs = res
    })
  }

  searchCode(){
    this.loggerService.findByCode(this.codeSearch).subscribe(res => {
      this.logs = res;
    });
  }

  searchDates(){
    console.log(this.startDate.toString());
    console.log(this.endDate.toString());
    this.loggerService.findByDates({
      "startDate": this.startDate,
      "endDate": this.endDate
    }).subscribe(res => {
      this.logs = res;
    })
  }

}
