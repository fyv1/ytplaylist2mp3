package pl.fyv.ytdownloader.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.util.Downloader;
import pl.fyv.ytdownloader.util.YtFetcher;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Service
public class YtDownloaderService {

    @Autowired
    YtFetcher fetcher;
    @Autowired
    Downloader downloader;

    final static Logger logger = LoggerFactory.getLogger(Downloader.class);

    public ArrayList<DownloadItemDTO> getPlaylistItems(String playlistId) {
        ArrayList<DownloadItemDTO> list = new ArrayList<>();
        try {
            list = fetcher.getPlaylist(playlistId);
        } catch (GoogleJsonResponseException e) {
            logger.warn(playlistId+" not found or private");
        } catch (GeneralSecurityException | IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        if(!list.isEmpty())
            return list;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Playlist not found or private");

    }

    public File downloadMp(String ytUrl) {
        File output = null;
        try {
            output = downloader.youtubeToMP3(ytUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}