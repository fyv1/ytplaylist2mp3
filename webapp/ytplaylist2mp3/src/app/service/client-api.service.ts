import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PlaylistItem } from '../domain/PlaylistItem';
;

@Injectable({
  providedIn: 'root'
})
export class ClientApiService {

  url = "http://localhost:8082/";

  constructor(private http: HttpClient) { }

  getPlaylistItems(url: string) : Observable<any> {
    var regExp = /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|playlist\?list=|\&v=)([^#\&\?]*).*/;
    var match = url.match(regExp);
    if (match) {
      return this.http.get(`${this.url}/api/playlist/${match[2]}`);
    } else {
      throw 'Invalid URL'
    }
  }
}
