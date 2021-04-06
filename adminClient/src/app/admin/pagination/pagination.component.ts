import { Component, OnInit, Input, Output, EventEmitter, OnChanges } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.sass']
})
export class PaginationComponent implements OnInit, OnChanges {
  @Input() totalPages: number;
  @Input() pageSize: number;
  @Output() pageSelected: EventEmitter<number>;
  pages: number[];
  activePage: number;
  collectionSize: number;

  constructor(

  ) {
    this.pageSelected = new EventEmitter();
    this.activePage = 1;
  }

  ngOnInit() {
    this.pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      this.pages.push(i);
    }
    this.collectionSize = this.totalPages * this.pageSize;
  }

  ngOnChanges(changes) {
    this.totalPages = changes.totalPages.currentValue;
    this.pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      this.pages.push(i);
    }
    this.collectionSize = this.totalPages * this.pageSize;
  }

  selected(newPage: number) {
    if (newPage >= 1 && newPage <= this.totalPages) {
      this.activePage = newPage;
      this.pageSelected.emit(this.activePage);
    }
  }
}
