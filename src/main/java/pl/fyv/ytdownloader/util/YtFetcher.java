package pl.fyv.ytdownloader.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;
import pl.fyv.ytdownloader.domain.exception.VideoProcessingException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Component
public class YtFetcher {
    final Logger logger = LoggerFactory.getLogger(YtFetcher.class);
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    @Value("${ytdl.developer.key}")
    private String DEVELOPER_KEY;
    @Value("${ytdl.application.name}")
    private String APPLICATION_NAME;

    private long ytPaginationMaxResult = 50L;

    public ArrayList<DownloadItemDTO> getPlaylist(String playlistId) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        ArrayList<DownloadItemDTO> list = new ArrayList<>();
        YouTube youtubeService = getService();

        String nextPageToken = "";
        while (nextPageToken != null) {
            YouTube.PlaylistItems.List playlistItemsListRequest = youtubeService.playlistItems().list("snippet");
            playlistItemsListRequest.setMaxResults(ytPaginationMaxResult);
            playlistItemsListRequest.setPageToken(nextPageToken);

            PlaylistItemListResponse playlistItemsListResponse = playlistItemsListRequest
                    .setKey(DEVELOPER_KEY)
                    .setPlaylistId(playlistId)
                    .execute();
            logger.info("Playlist " + playlistId + " in processing (yt pagination: "+ nextPageToken+", max pagination items: "+ ytPaginationMaxResult+")");

            for (PlaylistItem playlistItem : playlistItemsListResponse.getItems()) {
                logger.info("Currently processing: " + playlistItem.getSnippet().getTitle() + ", id: " + playlistItem.getSnippet().getResourceId().getVideoId());
                DownloadItemDTO dto = new DownloadItemDTO();
                dto.setTitle(playlistItem.getSnippet().getTitle());
                dto.setVideoId(playlistItem.getSnippet().getResourceId().getVideoId());
                if(playlistItem.getSnippet().getThumbnails().getDefault()!=null) { // if default thumbnail is not available, it means something wrong is with video on YT, e.g. it might be deleted
                    dto.setThumbnailUrl(playlistItem.getSnippet().getThumbnails().getDefault().getUrl());
                    list.add(dto);
                }
            }
            nextPageToken = playlistItemsListResponse.getNextPageToken();
        }

        return list;
    }

    public DownloadItemDTO getSingleMp3Info(String vidId) throws GeneralSecurityException, IOException {
        YouTube youtubeService = getService();

        YouTube.Videos.List videosListRequest = youtubeService.videos().list("snippet");
        VideoListResponse response = videosListRequest
                .setKey(DEVELOPER_KEY)
                .setId(vidId)
                .execute();
        logger.info("Video: "+ vidId + " in processing");
        DownloadItemDTO dto = new DownloadItemDTO();
        Video tempItem = response.getItems().get(0);
        dto.setTitle(tempItem.getSnippet().getTitle());
        dto.setVideoId(tempItem.getId());
        if(tempItem.getSnippet().getThumbnails().getDefault()!=null)  { // if default thumbnail is not available, it means something wrong is with video on YT, e.g. it might be deleted
            dto.setThumbnailUrl(tempItem.getSnippet().getThumbnails().getDefault().getUrl());
            return dto;
        }
        throw new VideoProcessingException();
    }

    private YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
