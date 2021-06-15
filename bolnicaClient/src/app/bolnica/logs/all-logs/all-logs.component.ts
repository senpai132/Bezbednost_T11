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

  constructor(
    private toastr: ToastrService,
    private loggerService: LoggerService
  ) { }

  ngOnInit(): void {
    this.loggerService.getAll().subscribe(res => {
      this.logs = res
    })
  }

}
