package pl.fyv.ytdownloader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.service.YtDownloaderService;

import java.io.File;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/")
public class YoutubeDownloaderController {
    @Autowired
    YtDownloaderService service;

    @GetMapping("/playlist/{id}")
    public ResponseEntity<ArrayList<DownloadItemDTO>> getPlaylist(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.getPlaylistItems(id), HttpStatus.OK);
    }

    //todo downloading
    @GetMapping("/video/{id}")
    public ResponseEntity<File> downloadFile(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.downloadMp("https://www.youtube.com/watch?v="+id, "C:\\Users\\fyv\\Desktop\\yt2mp3\\"), HttpStatus.OK);
    }
}
