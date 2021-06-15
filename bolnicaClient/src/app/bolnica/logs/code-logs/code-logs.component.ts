import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { LogEntry } from '../../model/log-entry';
import { LoggerService } from '../../services/logger.service';

@Component({
  selector: 'app-code-logs',
  templateUrl: './code-logs.component.html',
  styleUrls: ['./code-logs.component.sass']
})
export class CodeLogsComponent implements OnInit {

  logs: LogEntry[];

  constructor(
    private toastr: ToastrService,
    private loggerService: LoggerService
  ) { }

  ngOnInit(): void {
    this.loggerService.findByCode("ERR").subscribe(res => {
      this.logs = res
    })
  }


}
