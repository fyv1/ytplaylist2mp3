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

  saveVideoUrl(videoUrl: string) {
    localStorage.setItem("videoUrl", videoUrl)
  }
  
  getVideoUrl() {
    return localStorage.getItem("videoUrl")
  }

  clearPlaylistUrl() {
    localStorage.removeItem("playlistUrl")
  }

  clearVideoUrl() {
    localStorage.removeItem("videoUrl")
  }

}
