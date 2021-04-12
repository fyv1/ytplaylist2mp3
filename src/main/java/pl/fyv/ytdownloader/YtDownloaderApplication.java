package pl.fyv.ytdownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class YtDownloaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(YtDownloaderApplication.class, args);
    }
}
