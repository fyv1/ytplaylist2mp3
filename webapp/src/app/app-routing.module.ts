import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlaylisturlSubmitComponent } from './component/playlisturl-submit/playlisturl-submit.component';
import { PlaylistvideoListComponent } from './component/playlistvideo-list/playlistvideo-list.component';
import { DownloadItemComponent } from './component/download-item/download-item.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: PlaylisturlSubmitComponent },
  { path: 'list', pathMatch: 'full', component: PlaylistvideoListComponent },
  { path: 'download/:videoId', pathMatch: 'full', component: DownloadItemComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
