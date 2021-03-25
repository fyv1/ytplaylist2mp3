import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'

import { AppRoutingModule } from './app-routing.module'
import { AppComponent } from './app.component'
import { PlaylisturlSubmitComponent } from './component/playlisturl-submit/playlisturl-submit.component'
import { PlaylistvideoListComponent } from './component/playlistvideo-list/playlistvideo-list.component'
import { HttpClientModule } from '@angular/common/http'
import { FormsModule } from '@angular/forms';
import { Singlemp3urlSubmitComponent } from './component/singlemp3url-submit/singlemp3url-submit.component';
import { Singlemp3urlDownloadComponent } from './component/singlemp3url-download/singlemp3url-download.component'

@NgModule({
  declarations: [
    AppComponent,
    PlaylisturlSubmitComponent,
    PlaylistvideoListComponent,
    Singlemp3urlSubmitComponent,
    Singlemp3urlDownloadComponent
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
