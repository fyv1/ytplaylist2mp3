package pl.fyv.ytdownloader;

import pl.fyv.ytdownloader.utils.Downloader;

import java.io.File;
import java.io.IOException;

public class YtDownloaderApplication {
    public static void main(String[] args) throws IOException {
        String youtubeUrl = "https://www.youtube.com/watch?v=1TXF5xa3Vmk";
//        String youtubeUrl = "https://www.youtube.com/watch?v=p7Q0kpqgl9E";
        System.out.println("start");
        File mp3 = Downloader.youtubeToMP3(youtubeUrl, "C:\\Users\\Grzesiek\\Desktop\\ytdownloader\\");
        System.out.println("end");
        System.out.println(mp3.getName() + " downloaded");
    }
}
