package pl.fyv.ytdownloader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.service.YtDownloaderService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class YoutubeDownloaderController {
    @Autowired
    YtDownloaderService service;

    @GetMapping("/playlist/{id}")
    public ResponseEntity<ArrayList<DownloadItemDTO>> getPlaylist(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.getPlaylistItems(id), HttpStatus.OK);
    }

    // todo delete a file after some time after downloading OR download file directly to client
    @GetMapping(value = "/video/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") String id) throws FileNotFoundException {
        var file = service.downloadMp(id);
        InputStreamResource resource = new InputStreamResource((new FileInputStream(file)));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION)
                .contentLength(file.length())
                .body(resource);
    }

    @DeleteMapping("/video/{id}")
    public void deleteFile(@PathVariable("id") String id) {
        service.removeFile(id);
    }
}
