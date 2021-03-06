# Youtube Playlist to MP3 Downloader Online

YtPlaylist2Mp3 is an online application that allows you to download all your favourite music you already store in YT Playlist.
Just put your playlist URL and wait until all job is done, then download mp3 files on your device!

No login, no external tools, no ads, everything's free and open sourced!

[Click here to download playlist!](http://yt2mp3.pl)

### Features
- Download YT Playlist items one by one
- Everything goes online, you just wait for your download links
- Server logging every activity
- Internal CRON for clearing download path
- Support for external CRON
- Caching playlist results
<!--- - SOON! Import your Spotify playlist to YT or vice-versa!) -->

### Technology
**Backend**
- Java JDK 8
- Spring Boot 2.4.3
- Google API Client 1.23.00
- Google API Services v3-rev222 1.25.0
- Log4J 2.7
- Maven 3.6.1 (POM Model 4.0.0)

**Frontend**
- Typescript 4.1.5
- Angular 11.2.5
- Bootstrap 4.6.0
- RxJS 6.6.0
- NPM 6.9.0

### Run application
1. Change `application.properties.example` to `application.properties` in `resources`
1. If necessary change `server.port`
1. Add YouTube developer key and Google application name
1. Change URL in `\webapp\src\app\Constants.ts`
1. Run commands:
```
mvn spring-boot:run
cd webapp
npm install
ng serve
```
Server will be available at `localhost:8082` and the fronted app at `localhost:4200`.

### Configure HTTPS
You can run app locally with SSL. You should generate Self-Signed key with `keytool`. In `application.properties` configure `server.ssl.key-store` property and set `server.ssl.enabled` to true.
Next, you can run Angular app with `ng serve --ssl` - it will create temporary certificate. Remember to set new Backend url in `Constants.ts`.
