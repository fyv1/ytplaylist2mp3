import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Singlemp3urlSubmitComponent } from './singlemp3url-submit.component';

describe('Singlemp3urlSubmitComponent', () => {
  let component: Singlemp3urlSubmitComponent;
  let fixture: ComponentFixture<Singlemp3urlSubmitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Singlemp3urlSubmitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(Singlemp3urlSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
