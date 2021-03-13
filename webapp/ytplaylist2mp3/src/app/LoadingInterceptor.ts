import { Injectable } from '@angular/core';
import {
  HttpEvent,  
  HttpRequest,
  HttpHandler,
  HttpInterceptor,
  HttpResponse
} from '@angular/common/http';
import { finalize } from 'rxjs/operators';
import { of } from 'rxjs';
import { ClientApiService } from './service/client-api.service';
import { Observable } from 'rxjs';

@Injectable()
export class LoadingInterceptor implements HttpInterceptor {
  private count = 0;

  constructor(private loadingService: ClientApiService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
    if (this.count === 0) {
        this.loadingService.setHttpProgressStatus(true);
      }
      this.count++;
      return next.handle(request).pipe(
        finalize(() => {
          this.count--;
          if (this.count === 0) {
            this.loadingService.setHttpProgressStatus(false);
          }
        }));
    }
  }
}