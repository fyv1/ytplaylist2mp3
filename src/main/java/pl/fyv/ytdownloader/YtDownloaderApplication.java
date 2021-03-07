package pl.fyv.ytdownloader;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import pl.fyv.ytdownloader.utils.Downloader;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class YtDownloaderApplication {

    private static final String DEVELOPER_KEY = "secret :)";

    private static final String APPLICATION_NAME = "secret :)";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /*public static void main(String[] args)
            throws GeneralSecurityException, IOException, GoogleJsonResponseException {
        YouTube youtubeService = getService();
        // Define and execute the API request
        YouTube.PlaylistItems.List request = youtubeService.playlistItems()
                .list("contentDetails");
        PlaylistItemListResponse response = request.setKey(DEVELOPER_KEY)
                .setPlaylistId("PLnEiN3g9RA7TXThKtggHY65_QOkR98kf2") // mine summersongs
                .execute();
        System.out.println(response);
    }*/
    /*
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com/watch?v=N3cP0nr9JxQ";

        File mp3 = Downloader.youtubeToMP3(youtubeUrl, "C:\\Users\\fyv\\Desktop\\yt2mp3\\");

        System.out.println(mp3.getName() + " downloaded");
    }
*/
    private static YouTube getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new YouTube.Builder(httpTransport, JSON_FACTORY, null)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
