package pl.fyv.ytdownloader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.utils.Downloader;
import pl.fyv.ytdownloader.utils.YtFetcher;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Service
public class YtDownloaderService {

    @Autowired
    YtFetcher fetcher;

    public ArrayList<DownloadItemDTO> getPlaylistItems(String playlistId) {
        ArrayList<DownloadItemDTO> list = new ArrayList<>();
        try {
            list = fetcher.getPlaylist(playlistId);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public File downloadMp(String ytUrl, String pathToSave) {
        File output = null;
        try {
            output = Downloader.youtubeToMP3(ytUrl, pathToSave);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
}