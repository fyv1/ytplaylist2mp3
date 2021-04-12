package pl.fyv.ytdownloader.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.domain.exception.VideoProcessingException;
import pl.fyv.ytdownloader.util.Downloader;
import pl.fyv.ytdownloader.util.YtFetcher;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Service
@CacheConfig(cacheNames = "downloadItems")
public class YtDownloaderService {

    @Autowired
    YtFetcher fetcher;
    @Autowired
    Downloader downloader;
    @Value("${ytdl.download.path}")
    private String path;
    @Autowired
    CacheManager cacheManager;

    private ArrayList<DownloadItemDTO> list = new ArrayList<>();

    final static Logger logger = LoggerFactory.getLogger(Downloader.class);

    @Cacheable
    public ArrayList<DownloadItemDTO> getPlaylistItems(String playlistId) {
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

    public DownloadItemDTO getSingleVideoInfo(String vidId) {
        DownloadItemDTO dto = null;
        try {
            dto = fetcher.getSingleMp3Info(vidId);
        } catch (VideoProcessingException e) {
            logger.warn(vidId+" processing error (video private or not found)");
        } catch (GeneralSecurityException | IOException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }
        if(dto!=null)
            return dto;
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Video not found or private");
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

    public void removeFile(String name) {
        File obj = new File(path+name);
        if (obj.delete())
            logger.info("Deleted the file: " + obj.getName());
        else
            logger.info("Failed to delete the file.");
    }

    @CacheEvict(allEntries = true)
    public void clearDir() {
        File dir = new File(path);
        for(File file: dir.listFiles())
            if (!file.isDirectory())
                file.delete();
        logger.info("Dir path cleared!");

        if(!cacheManager.getCacheNames().isEmpty())
            cacheManager.getCache("downloadItems").clear();
        logger.info("Cache cleared!");
    }
}