# Youtube Playlist to MP3 Downloader Online

YtPlaylist2Mp3 is an online application that allows you to download all your favourite music you already store in YT Playlist.
Just put your playlist URL and wait until all job is done, then download mp3 files on your device!

No login, no external tools, no ads, everything's free and open sourced!

[Click here to download playlist!](# "add link here")

### Features
- Download YT Playlist items one by one
- SOON! Import your Spotify playlist to YT or vice-versa!
- Everything goes online, you just wait for your download links
- Server logging every activity

### Technology
**Backend**
- Java JDK 11
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
cd ytplaylist2mp3
npm install
ng serve
```
Server will be available at `localhost:8082` and the fronted app at `localhost:4200`.
 
