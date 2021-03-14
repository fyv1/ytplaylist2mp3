import { Component, OnInit } from '@angular/core';
import { YoutubePlaylist } from '../domain/YoutubePlaylist';
import { ClientApiService } from './../service/client-api.service';
import { Router } from '@angular/router';
import { InnerService } from '../service/inner.service';

@Component({
  selector: 'app-playlisturl-submit',
  templateUrl: './playlisturl-submit.component.html',
  styleUrls: ['./playlisturl-submit.component.css']
})
export class PlaylisturlSubmitComponent implements OnInit {

  ytPlaylist: YoutubePlaylist = new YoutubePlaylist();

  constructor(private router: Router,
    private innerService: InnerService) { }

  ngOnInit(): void {
  }

  onUrlSubmitted() {
    this.innerService.savePlaylistUrl(this.ytPlaylist);
    this.router.navigateByUrl('/list');
  }

}
