import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodeLogsComponent } from './code-logs.component';

describe('CodeLogsComponent', () => {
  let component: CodeLogsComponent;
  let fixture: ComponentFixture<CodeLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodeLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodeLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
