import { Component, DoCheck, Input, OnInit } from '@angular/core';
import { FieldDecorator } from './field-decorator';
import { TableHeader } from './table-header';
import {
  faPlus, faPencilAlt, faTrash, faNewspaper,
  faEye, IconDefinition, faArrowRight, faEyeSlash
} from '@fortawesome/free-solid-svg-icons';
import { Icons } from 'src/app/enums/icons.enum';
import { TableOperation } from './table-operation';
import { iif } from 'rxjs';

@Component({
  selector: 'app-gen-table',
  templateUrl: './gen-table.component.html',
  styleUrls: ['./gen-table.component.sass']
})
export class GenTableComponent<T> implements OnInit, DoCheck {

  @Input() tableData: T[] = [];
  @Input() tableHeader: TableHeader[] = [];
  @Input() rowNum = false;
  @Input() fieldDecoration: FieldDecorator;
  @Input() operations: TableOperation<T>[] = [];
  data: T[];
  page = 1;
  pageSize = 20;
  collectionSize = 0;

  constructor() { }

  ngOnInit(): void {
    if(this.tableData) {
      this.collectionSize = this.tableData.length;
    }
  }

  ngDoCheck(): void {
    if(this.tableData) {
      this.collectionSize = this.tableData.length;
      this.refreshItems();
    }
  }

  refreshItems(): void {
    this.data = this.tableData
      .map((item, i) => {
        if (this.rowNum) {
          return { tableId: i + 1, ...item };
        } else {
          return item;
        }
      });
  }

  generateField(item, headerInfo): string {
    let retVal: string;
    if (item) {
      const rowValues: string[] = headerInfo.fieldName.map((elem, i) => item[elem] + '');
      if (this.fieldDecoration && headerInfo.headerName === this.fieldDecoration.name) {
        retVal = this.fieldDecoration.decoration.replace(/\{(\d+)\}/g,
          (match, capture) => {
            return rowValues[1 * capture];
          });
      } else {
        retVal = rowValues.join(' ');
      }
    }

    return retVal;
  }

  operationIcon(iconName): IconDefinition {
    let iconDef: IconDefinition;

    switch (iconName) {
      case Icons.add:
        iconDef = faPlus;
        break;
      case Icons.news:
        iconDef = faNewspaper;
        break;
      case Icons.preview:
        iconDef = faEye;
        break;
      case Icons.remove:
        iconDef = faTrash;
        break;
      case Icons.update:
        iconDef = faPencilAlt;
        break;
      case Icons.arrowRight:
        iconDef = faArrowRight;
        break;
      case Icons.slashPreview:
        iconDef = faEyeSlash;
        break;
    }

    return iconDef;
  }
}