import { TestBed } from '@angular/core/testing';

import { InnerService } from './inner.service';

describe('InnerService', () => {
  let service: InnerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InnerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
