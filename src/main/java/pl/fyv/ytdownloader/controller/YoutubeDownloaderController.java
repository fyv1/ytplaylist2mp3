package pl.fyv.ytdownloader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.service.YtDownloaderService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class YoutubeDownloaderController {
    @Autowired
    YtDownloaderService service;

    @Value("${ytdl.using.internal.cron}")
    boolean useInternalCron;

    @GetMapping("/playlist/{id}")
    public ResponseEntity<ArrayList<DownloadItemDTO>> getPlaylist(@PathVariable("id") String id) {
        return new ResponseEntity<>(service.getPlaylistItems(id), HttpStatus.OK);
    }

    @GetMapping(value = "/video/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("id") String id) throws FileNotFoundException {
        File file = service.downloadMp(id);
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

    @Scheduled(cron = "0 0 4 * * *") // at 4am every day
    @DeleteMapping("/admin/directory")
    public void clearDir() {
        if(useInternalCron)
            service.clearDir();
    }
}
