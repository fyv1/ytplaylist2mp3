import { Injectable } from '@angular/core'
import { HttpClient, HttpResponse } from '@angular/common/http'
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

  getSingleMp3Item(url: string) : any {
    let regExp = /^((?:https?:)?\/\/)?((?:www|m)\.)?((?:youtube\.com|youtu.be))(\/(?:[\w\-]+\?v=|embed\/|v\/)?)([\w\-]+)(\S+)?$/
    let match = url.match(regExp)
    if (match == null) {
      throw new InvalidUrlException("Invalid video URL")
    } else if (match) {
      return this.http.get(`${this.url}/api/mp3/${match[5]}`)
      .pipe(
        catchError(() => {
          alert("Video not found or private")
          this.router.navigateByUrl('/')
          return throwError(new VideoNotFoundException("Video not found or private"))
        })
      )
    } else {
      throw new InvalidUrlException("Invalid video URL")
    }
  }

  downloadItem(videoId: string) : Observable<HttpResponse<Blob>> {
    return this.http.get<Blob>(`${this.url}/api/video/${videoId}`, {
      responseType: 'blob' as 'json',
      observe: 'response',
      reportProgress: true
    })
  }

  removeFileFromServer(filename: string) : void {
    this.http.delete(`${this.url}/api/video/${filename}`)
    .subscribe()
  }

}
