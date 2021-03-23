import { Component, OnInit } from '@angular/core'
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ClientApiService } from '../../service/client-api.service'
import { Router } from '@angular/router'
import { InnerService } from '../../service/inner.service'
import { PlaylistItem } from '../../domain/PlaylistItem'
import { InvalidUrlException } from '../../exceptions/InvalidUrlException'
import { PlaylistNotFoundException } from '../../exceptions/PlaylistNotFoundException'
import { HttpResponse, HttpEventType } from '@angular/common/http'
import { VideoNotFoundException } from 'src/app/exceptions/VideoNotFoundException'

@Component({
  selector: 'app-playlistvideo-list',
  templateUrl: './playlistvideo-list.component.html',
  styleUrls: ['./playlistvideo-list.component.css']
})
export class PlaylistvideoListComponent implements OnInit {

  ytPlaylistUrl: string = this.innerService.getPlaylistUrl()
  playlistItems: PlaylistItem[] //Observable<PlaylistItem[]>
  loading = false
  downloading = false
  filename: string

  constructor(private service: ClientApiService,
    private router: Router,
    private innerService: InnerService,
    private sanitizer: DomSanitizer) {
    }

  ngOnInit(): void {
    this.getPlaylistItems()
  }

  ngOnDestroy(): void {
    // making sure localStorage is cleared
    this.innerService.clearPlaylistUrl()
  }

  getPlaylistItems() {
      try {
        this.loading = true
        this.service.getPlaylistItems(this.ytPlaylistUrl)
          .subscribe(items=> {
            this.playlistItems = items
            this.loading = false
            this.innerService.clearPlaylistUrl()

            this.playlistItems.forEach(function(it) {
              it.currentState = 'readyToDownload'
            })

          })
      } catch (error) {
        if(error instanceof InvalidUrlException)
          alert("Invalid URL pattern! Paste correct Playlist url from Youtube")
        if(error instanceof PlaylistNotFoundException)
          alert("Playlist not found or private")
        if(error instanceof TypeError)
          alert("An error occured! Try again.")

        console.error(error)
        this.router.navigateByUrl('/')
      }
  }

  downloadRequest(item) {
    try {
      let vidId = item.videoId
      item.currentState = 'downloading'
      this.service.downloadItem(vidId)
        .subscribe((resp: HttpResponse<Blob>)  => {
          let data = resp.body
          let filename = "download.mp3"
          let contentDisposition = resp.headers.get('content-disposition')
          if (contentDisposition && contentDisposition.indexOf('attachment') !== -1) {
            let filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
            let matches = filenameRegex.exec(contentDisposition)
            if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '')
          }
          let a = document.createElement('a')
          let objectUrl = URL.createObjectURL(data)
          a.href = objectUrl
          a.download = filename
          a.click()
          URL.revokeObjectURL(objectUrl)
          item.currentState = 'readyToDownload'
        })
    } catch (error) {
      if(error instanceof VideoNotFoundException)
        alert("Video not found")
      if(error instanceof TypeError)
        alert("An error occured! Try again.")

      console.error(error)
      this.router.navigateByUrl('/')
    }
  }

  removeItemFromDisk() {
    let tempArr
    if(this.filename.includes("/"))
      tempArr = this.filename.split("/")
    else if(this.filename.includes("\\")) 
      tempArr = this.filename.split("\\")
    
    console.log(tempArr[tempArr.length-1])
    this.service.removeFileFromServer(tempArr[tempArr.length-1])
  }

}
