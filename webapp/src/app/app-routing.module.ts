import { NgModule } from '@angular/core'
import { RouterModule, Routes } from '@angular/router'
import { PlaylisturlSubmitComponent } from './component/playlisturl-submit/playlisturl-submit.component'
import { PlaylistvideoListComponent } from './component/playlistvideo-list/playlistvideo-list.component'
import { Singlemp3urlSubmitComponent } from './component/singlemp3url-submit/singlemp3url-submit.component'
import { Singlemp3urlDownloadComponent } from './component/singlemp3url-download/singlemp3url-download.component'

const routes: Routes = [
  { path: '', pathMatch: 'full', component: PlaylisturlSubmitComponent },
  { path: 'list', pathMatch: 'full', component: PlaylistvideoListComponent },
  { path: 'singlemp3', pathMatch: 'full', component: Singlemp3urlSubmitComponent },
  { path: 'downloadmp3', pathMatch: 'full', component: Singlemp3urlDownloadComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
