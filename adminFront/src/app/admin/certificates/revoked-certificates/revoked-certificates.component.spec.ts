import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RevokedCertificatesComponent } from './revoked-certificates.component';

describe('RevokedCertificatesComponent', () => {
  let component: RevokedCertificatesComponent;
  let fixture: ComponentFixture<RevokedCertificatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RevokedCertificatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RevokedCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
