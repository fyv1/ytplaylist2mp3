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
  filename: string

  constructor(private route: ActivatedRoute,
    private router: Router,
    private service: ClientApiService) {
    this.videoId = this.route.snapshot.params['videoId']
   }

  ngOnInit(): void {
    // this.downloadItemRequest()
  }

  ngOnDestroy(): void {
    this.removeItemFromDisk()
  }

  // downloadItemRequest() {
  //   try {
  //     this.loading = true
  //     this.service.downloadItem(this.videoId)
  //       .subscribe(item => {
  //         this.filename = item
  //         this.loading = false
  //       })
  //   } catch (error) {
  //     if(error instanceof VideoNotFoundException)
  //       alert("Video not found")
  //     if(error instanceof TypeError)
  //       alert("An error occured! Try again.")

  //     console.error(error)
  //     this.router.navigateByUrl('/')
  //   }
  // }

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
