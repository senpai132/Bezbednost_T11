import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { LogEntry } from '../../model/log-entry';
import { LoggerService } from '../../services/logger.service';

@Component({
  selector: 'app-type-logs',
  templateUrl: './type-logs.component.html',
  styleUrls: ['./type-logs.component.sass']
})
export class TypeLogsComponent implements OnInit {

  logs: LogEntry[];

  constructor(
    private toastr: ToastrService,
    private loggerService: LoggerService
  ) { }

  ngOnInit(): void {
    this.loggerService.findByType("DEVICE_ALARM").subscribe(res => {
      this.logs = res
    })
  }

}
