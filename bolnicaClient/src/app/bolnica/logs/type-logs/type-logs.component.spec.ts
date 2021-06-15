import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TypeLogsComponent } from './type-logs.component';

describe('TypeLogsComponent', () => {
  let component: TypeLogsComponent;
  let fixture: ComponentFixture<TypeLogsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TypeLogsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TypeLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
