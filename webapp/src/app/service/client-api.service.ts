import { Injectable } from '@angular/core'
import { HttpClient, HttpErrorResponse } from '@angular/common/http'
import { Router } from '@angular/router'
import { Observable, throwError } from 'rxjs'
import { tap, catchError } from 'rxjs/operators'
import { InvalidUrlException } from './../exceptions/InvalidUrlException'
import { PlaylistNotFoundException } from '../exceptions/PlaylistNotFoundException'
import { Constants } from '../Constants'
import { VideoNotFoundException } from '../exceptions/VideoNotFoundException'

@Injectable({
  providedIn: 'root'
})
export class ClientApiService {

  url = Constants.SERVER_URL

  constructor(private http: HttpClient, private router: Router) { }

  getPlaylistItems(url: string) : Observable<any> {
    let regExp = /^.*(youtu\.be\/|v\/|u\/\w\/|embed\/|playlist\?list=|\&v=)([^#\&\?]*).*/
    let match = url.match(regExp)
    if (match == null) {
      throw new InvalidUrlException("Invalid playlist URL")
    } else if (match) {
       return this.http.get(`${this.url}/api/playlist/${match[2]}`)
        .pipe(
          catchError(() => {
            alert("Playlist not found or private")
            this.router.navigateByUrl('/')
            return throwError(new PlaylistNotFoundException("Playlist not found or private"))
          }),

          tap(items => {
            console.log(`items fetched: ${items.length}`)
          })
        )
    } else {
      throw new InvalidUrlException("Invalid playlist URL")
    }
  } 
  
  downloadItem(videoId: string) : Observable<any> {
    return this.http.get(`${this.url}/api/video/${videoId}`)
        .pipe(
          catchError(() => {
            alert("Video not found")
            this.router.navigateByUrl('/')
            return throwError(new VideoNotFoundException("Video not found"))
          }),

          tap(item => {
            console.log(`downloaded: ${item}`)
          })
        )
  }

  removeFileFromServer(filename: string) : void {
    this.http.delete(`${this.url}/api/video/${filename}`)
    .subscribe()
  }

}
