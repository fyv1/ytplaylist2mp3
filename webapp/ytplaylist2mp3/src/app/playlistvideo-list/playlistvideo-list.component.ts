import { Component, OnInit, Renderer2 } from '@angular/core'
import { Observable } from 'rxjs'
import { ClientApiService } from './../service/client-api.service'
import { Router } from '@angular/router'
import { InnerService } from '../service/inner.service'
import { PlaylistItem } from '../domain/PlaylistItem'
import { InvalidUrlException } from './../exceptions/InvalidUrlException'

@Component({
  selector: 'app-playlistvideo-list',
  templateUrl: './playlistvideo-list.component.html',
  styleUrls: ['./playlistvideo-list.component.css']
})
export class PlaylistvideoListComponent implements OnInit {

  ytPlaylistUrl: string = this.innerService.getPlaylistUrl()
  playlistItems: Observable<PlaylistItem[]>
  loading = false

  constructor(private service: ClientApiService,
    private router: Router,
    private innerService: InnerService) { 
    }

  ngOnInit(): void {
    this.getPlaylistItems()
  }

  getPlaylistItems() {
      try {
        this.loading = true
        this.service.getPlaylistItems(this.ytPlaylistUrl)
          .subscribe(items=> {
            this.playlistItems = items
            this.loading = false
            this.innerService.clearPlaylistUrl()
          })
      } catch (error) {
        if(error instanceof InvalidUrlException) 
            alert("Invalid URL pattern! Paste correct Playlist url from Youtube")
        
        if(error instanceof TypeError)
          alert("An error occured! Try again.")
        
        
        console.error(error)
        this.router.navigateByUrl('/')
      } 
  }

}
