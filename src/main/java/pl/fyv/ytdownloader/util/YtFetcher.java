package pl.fyv.ytdownloader.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;

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

    public ArrayList<DownloadItemDTO> getPlaylist(String playlistId) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        ArrayList<DownloadItemDTO> list = new ArrayList<>();
        YouTube youtubeService = getService();

        String nextPageToken = "";
        while (nextPageToken != null) {
            YouTube.PlaylistItems.List playlistItemsListRequest = youtubeService.playlistItems().list("snippet");
            playlistItemsListRequest.setMaxResults(50L);
            playlistItemsListRequest.setPageToken(nextPageToken);

            PlaylistItemListResponse playlistItemsListResponse = playlistItemsListRequest
                    .setKey(DEVELOPER_KEY)
                    .setPlaylistId(playlistId)
                    .execute();
            logger.info("Playlist " + playlistId + " in processing");

            for (PlaylistItem playlistItem : playlistItemsListResponse.getItems()) {
                logger.info("Currently proccessing: " + playlistItem.getSnippet().getTitle() + ", id: " + playlistItem.getSnippet().getResourceId().getVideoId());
                DownloadItemDTO dto = new DownloadItemDTO();
                dto.setTitle(playlistItem.getSnippet().getTitle());
                dto.setVideoId(playlistItem.getSnippet().getResourceId().getVideoId());
                dto.setThumbnailUrl(playlistItem.getSnippet().getThumbnails().getDefault().getUrl());
                list.add(dto);
            }
            nextPageToken = playlistItemsListResponse.getNextPageToken();
        }

        return list;
    }

    private YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
