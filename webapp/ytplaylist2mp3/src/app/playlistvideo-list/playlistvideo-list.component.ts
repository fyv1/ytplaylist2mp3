import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientApiService } from './../service/client-api.service';
import { Router } from '@angular/router';
import { InnerService } from '../service/inner.service';
import { PlaylistItem } from '../domain/PlaylistItem';

@Component({
  selector: 'app-playlistvideo-list',
  templateUrl: './playlistvideo-list.component.html',
  styleUrls: ['./playlistvideo-list.component.css']
})
export class PlaylistvideoListComponent implements OnInit {

  ytPlaylistUrl: string = this.innerService.getPlaylistUrl();
  playlistItems: Observable<PlaylistItem[]>

  constructor(private service: ClientApiService,
    private router: Router,
    private innerService: InnerService) {
      if(this.ytPlaylistUrl) {
        this.getPlaylistItems();
      } else {
        this.router.navigateByUrl('/');
      }
     }

  ngOnInit(): void {
  }

  getPlaylistItems() {
      try {
        this.playlistItems = this.service.getPlaylistItems(this.ytPlaylistUrl);
      } catch (error) {
        // todo send info that playlist url is incorrect
        this.router.navigateByUrl('/');
      } 
  }

}
