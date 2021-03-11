import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylistvideoListComponent } from './playlistvideo-list.component';

describe('PlaylistvideoListComponent', () => {
  let component: PlaylistvideoListComponent;
  let fixture: ComponentFixture<PlaylistvideoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaylistvideoListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaylistvideoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
