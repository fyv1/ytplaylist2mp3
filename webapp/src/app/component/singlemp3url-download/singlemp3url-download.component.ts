import { Component, OnInit } from '@angular/core';
import { InnerService } from 'src/app/service/inner.service';
import { ClientApiService } from 'src/app/service/client-api.service';
import { Router } from '@angular/router';
import { InvalidUrlException } from 'src/app/exceptions/InvalidUrlException';
import { HttpResponse } from '@angular/common/http';
import { VideoNotFoundException } from 'src/app/exceptions/VideoNotFoundException';

@Component({
  selector: 'app-singlemp3url-download',
  templateUrl: './singlemp3url-download.component.html',
  styleUrls: ['./singlemp3url-download.component.css']
})
export class Singlemp3urlDownloadComponent implements OnInit {

  ytVideoUrl: string = this.innerService.getVideoUrl()
  downloading = false
  filename: string

  constructor(
    private innerService: InnerService,
    private service: ClientApiService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.downloadRequest()
  }

  ngOnDestroy(): void {
    // making sure localStorage is cleared
    this.innerService.clearVideoUrl()
  }

  downloadRequest() {
    let regExp = /^((?:https?:)?\/\/)?((?:www|m)\.)?((?:youtube\.com|youtu.be))(\/(?:[\w\-]+\?v=|embed\/|v\/)?)([\w\-]+)(\S+)?$/
    let match = this.ytVideoUrl.match(regExp)
    if (match == null) {
      throw new InvalidUrlException("Invalid video URL")
    } else if (match) {
      console.log(match)
      try {
        this.downloading = true
        this.service.downloadItem(match[5])
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
            this.downloading = false
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
  }


}
