import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../../gen-table/field-decorator';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { CertificateRequest } from '../../model/certificate-request';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-certificate-requests',
  templateUrl: './certificate-requests.component.html',
  styleUrls: ['./certificate-requests.component.sass']
})
export class CertificateRequestsComponent implements OnInit {

  requests: CertificateRequest[];
  currentPage: number;
  pageSize: number;
  totalSize: number;

  tableHeader: TableHeader[] = [
    {
      headerName: 'First Name',
      fieldName: ['firstName']
    },
    {
      headerName: 'Last Name',
      fieldName: ['lastName']
    },
    {
      headerName: 'Organization',
      fieldName: ['organization']
    },
    {
      headerName: 'Country',
      fieldName: ['country']
    },
    {
      headerName: 'Email',
      fieldName: ['email']
    },
    {
      headerName: 'Locality',
      fieldName: ['locality']
    }
  ];
  headerDecoration: FieldDecorator = {
    name: 'CertificateRequest',
    decoration: `<img src="https://upload.wikimedia.org/wikipedia/commons/{0}" class="mr-2" style="width: 20px"> {1}`
  };

  operations: TableOperation<CertificateRequest>[] = [
    {
      operation: (request: CertificateRequest) => this.accept(request.id),
      icon: Icons.remove
    },
    {
      operation: (request: CertificateRequest) => this.decline(request.id),
      icon: Icons.remove
    }
  ];

  constructor(
    private modalService: NgbModal,
    private CertificateService: CertificateService,
    private router: Router
    ) {
      this.currentPage=1;
      this.pageSize=10;
  }

  ngOnInit(): void {
    this.CertificateService.allRequests().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.requests = res;
      }
    );
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.CertificateService.allRequests().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.requests = res;
      }
    );
	}

  saveChange(): void{
    this.ngOnInit();
  }

  accept(id){
    this.CertificateService.acceptRequest(id).subscribe(res => {
      this.saveChange();
    });
  }

  decline(id){
    this.CertificateService.declineRequest(id).subscribe(res => {
      this.saveChange();
    });
  }

}
