import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PlaylisturlSubmitComponent } from './playlisturl-submit/playlisturl-submit.component';
import { PlaylistvideoListComponent } from './playlistvideo-list/playlistvideo-list.component';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: PlaylisturlSubmitComponent },
  { path: 'list', pathMatch: 'full', component: PlaylistvideoListComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
