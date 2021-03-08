package pl.fyv.ytdownloader.utils;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.fyv.ytdownloader.domain.DownloadItemDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Component
public class YtFetcher {
    private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    @Value("${ytdl.developer.key}")
    private String DEVELOPER_KEY;
    @Value("${ytdl.application.name}")
    private String APPLICATION_NAME;

    public ArrayList<DownloadItemDTO> getPlaylist(String playlistId) throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        ArrayList<DownloadItemDTO> list = new ArrayList<>();
        YouTube youtubeService = getService();

        var nextPageToken = "";
        while (nextPageToken != null) {
            var playlistItemsListRequest = youtubeService.playlistItems().list("snippet");
            playlistItemsListRequest.setMaxResults(50L);
            playlistItemsListRequest.setPageToken(nextPageToken);

            var playlistItemsListResponse = playlistItemsListRequest
                    .setKey(DEVELOPER_KEY)
                    .setPlaylistId(playlistId)
                    .execute();

            for (var playlistItem : playlistItemsListResponse.getItems()) {
                System.out.println("Currently proccessing: " + playlistItem.getSnippet().getTitle() + ", id: " + playlistItem.getSnippet().getResourceId().getVideoId());
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
