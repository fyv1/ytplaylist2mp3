package pl.fyv.ytdownloader;

import pl.fyv.ytdownloader.utils.Downloader;

import java.io.File;
import java.io.IOException;

public class YtDownloaderApplication {
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com/watch?v=N3cP0nr9JxQ";

        File mp3 = Downloader.youtubeToMP3(youtubeUrl, "C:\\Users\\fyv\\Desktop\\yt2mp3\\");

        System.out.println(mp3.getName() + " downloaded");
    }
}
