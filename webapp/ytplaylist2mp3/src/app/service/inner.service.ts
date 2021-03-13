import { Injectable } from '@angular/core';
import { YoutubePlaylist } from '../domain/YoutubePlaylist';

@Injectable({
  providedIn: 'root'
})
export class InnerService {

  constructor() { }

  savePlaylistUrl(playlist: YoutubePlaylist) {
    localStorage.setItem("playlistUrl", playlist.playlistUrl)
  }
  
  getPlaylistUrl() {
    return localStorage.getItem("playlistUrl")
  }

  clearPlaylistUrl() {
    localStorage.removeItem("playlistUrl")
  }

}
