import { Injectable } from '@angular/core'
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs'
import { tap } from 'rxjs/operators'
import { InvalidUrlException } from './../exceptions/InvalidUrlException'

@Injectable({
  providedIn: 'root'
})
export class ClientApiService {

  url = "http://localhost:8082/"

  constructor(private http: HttpClient) { }

  getPlaylistItems(url: string) : Observable<any> {
    let regExp = /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|playlist\?list=|\&v=)([^#\&\?]*).*/
    let match = url.match(regExp)
    if (match == null) {
      throw new InvalidUrlException("Invalid playlist URL")
    } else if (match) {
       return this.http.get(`${this.url}/api/playlist/${match[2]}`)
        .pipe(
          tap(items => {
            console.log(`items fetched: ${items.length}`)
          })
        )
    } else {
      throw new InvalidUrlException("Invalid playlist URL")
    }
  }  
}
