import { Component, OnInit } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { InvalidUrlException } from '../../exceptions/InvalidUrlException'
import { PlaylistNotFoundException } from '../../exceptions/PlaylistNotFoundException'
import { ClientApiService } from 'src/app/service/client-api.service'
import { VideoNotFoundException } from 'src/app/exceptions/VideoNotFoundException'

@Component({
  selector: 'app-download-item',
  templateUrl: './download-item.component.html',
  styleUrls: ['./download-item.component.css']
})
export class DownloadItemComponent implements OnInit {

  videoId: string
  loading = false

  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: ClientApiService) {
    this.videoId = this.route.snapshot.params['videoId']
   }

  ngOnInit(): void {
    this.downloadItemRequest()
  }

  ngOnDestroy(): void {
    
  }

  downloadItemRequest() {
    try {
      this.loading = true
      this.service.downloadItem(this.videoId)
        .subscribe(item => {
          this.loading = false
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
