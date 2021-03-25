import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Singlemp3urlDownloadComponent } from './singlemp3url-download.component';

describe('Singlemp3urlDownloadComponent', () => {
  let component: Singlemp3urlDownloadComponent;
  let fixture: ComponentFixture<Singlemp3urlDownloadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Singlemp3urlDownloadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Singlemp3urlDownloadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
