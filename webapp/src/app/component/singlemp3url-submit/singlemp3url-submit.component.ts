import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InnerService } from 'src/app/service/inner.service';

@Component({
  selector: 'app-singlemp3url-submit',
  templateUrl: './singlemp3url-submit.component.html',
  styleUrls: ['./singlemp3url-submit.component.css']
})
export class Singlemp3urlSubmitComponent implements OnInit {

  videoUrl: string

  constructor(private router: Router,
    private innerService: InnerService) { }

  ngOnInit(): void {
  }

  onUrlSubmitted() {
    this.innerService.saveVideoUrl(this.videoUrl);
    this.router.navigateByUrl('/downloadmp3');
  }

}
