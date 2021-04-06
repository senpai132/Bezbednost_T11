import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Icons } from 'src/app/enums/icons.enum';
import { FieldDecorator } from '../../gen-table/field-decorator';
import { TableHeader } from '../../gen-table/table-header';
import { TableOperation } from '../../gen-table/table-operation';
import { Certificate } from '../../model/certificate';
import { CertificateService } from '../../services/certificate.service';

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.sass']
})
export class AllCertificatesComponent implements OnInit {

  certificates: Certificate[];
  currentPage: number;
  pageSize: number;
  totalSize: number;

  tableHeader: TableHeader[] = [
    {
      headerName: 'Serial Number',
      fieldName: ['serialNumber']
    },
    {
      headerName: 'Start Date',
      fieldName: ['startDate']
    },
    {
      headerName: 'End Date',
      fieldName: ['endDate']
    },
    {
      headerName: 'Subject',
      fieldName: ['subjectName']
    },
    {
      headerName: 'Issuer',
      fieldName: ['issuerName']
    }
  ];
  headerDecoration: FieldDecorator = {
    name: 'Certificate',
    decoration: `<img src="https://upload.wikimedia.org/wikipedia/commons/{0}" class="mr-2" style="width: 20px"> {1}`
  };

  operations: TableOperation<Certificate>[] = [
    {
      operation: (certificate: Certificate) => this.remove(certificate.serialNumber),
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
    this.CertificateService.getAll().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.certificates = res;
      }
    );
  }

  changePage(newPage: number) {
    this.currentPage = newPage;
    this.CertificateService.getAll().subscribe(
      res => {
        this.totalSize = Math.ceil(res.length/this.pageSize);
        this.certificates = res;
      }
    );
	}

  saveChange(): void{
    this.ngOnInit();
  }

  remove(id){
    this.CertificateService.remove(id).subscribe(res => {
      this.saveChange();
    });
  }

}
