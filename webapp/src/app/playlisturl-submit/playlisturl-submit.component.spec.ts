import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlaylisturlSubmitComponent } from './playlisturl-submit.component';

describe('PlaylisturlSubmitComponent', () => {
  let component: PlaylisturlSubmitComponent;
  let fixture: ComponentFixture<PlaylisturlSubmitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlaylisturlSubmitComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlaylisturlSubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
