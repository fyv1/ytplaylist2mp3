package pl.fyv.ytdownloader.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Downloader {
    private static final Pattern VID_ID_PATTERN = Pattern.compile("(?<=v\\=|youtu\\.be\\/)\\w+");
    private static final Pattern MP3_URL_PATTERN = Pattern.compile("https\\:\\/\\/\\w+\\.ytapivmp3\\.com\\/download.+?(?=\\\")");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("(?<=filename=\").+?(?=\")");

    @Value("${ytdl.download.path}")
    private String path;
    private String tempName="";

    final static Logger logger = LoggerFactory.getLogger(Downloader.class);

    public File youtubeToMP3(String id) throws IOException {
        String converter = loadConverter(id);
        String mp3url = getMP3URL(converter);

        StringBuilder builder = new StringBuilder();
        byte[] mp3 = load(mp3url, builder);

        if(tempName.contains("\\")) tempName=tempName.replaceAll("\\\\", "_");
        if(tempName.contains("/")) tempName=tempName.replaceAll("/", "_");
        if(tempName.contains(":")) tempName=tempName.replaceAll(":", "_");
        if(tempName.contains("\"")) tempName=tempName.replaceAll("\"", "_");
        File output = new File(path+tempName);
        logger.info("File saved in: "+output.getAbsolutePath());
        FileOutputStream stream = new FileOutputStream(output);
        stream.write(mp3);
        stream.flush();
        stream.close();
        logger.info(builder+".mp3 saved correctly");

        return output;
    }

    private byte[] load(String url, StringBuilder filename) throws IOException {
        URL url2 = new URL(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
        connection.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
        connection.connect();

        if (filename != null) {
            String s = toUTF8(connection.getHeaderField("content-disposition"));
            if (s != null && !s.isEmpty()) {
                Matcher m = FILENAME_PATTERN.matcher(s);
                if (m.find()) {
                    filename.append(m.group());
                } else {
                    filename.append(s);
                }
            }
            replaceAll(filename, Pattern.compile(".mp3"), "");
        }

        InputStream is = connection.getInputStream();
        byte[] byteChunk = new byte[2500];
        int n;
        if(filename != null) {
            tempName = filename.toString() + "_" + new java.util.Date().getTime()+".mp3";
            logger.info(filename + ".mp3 saving to buffer");
        }
        while ((n = is.read(byteChunk)) > 0) {
            baos.write(byteChunk, 0, n);
        }
        connection.disconnect();
        baos.flush();
        baos.close();

        return baos.toByteArray();
    }

    private String toUTF8(String s) {
        try {
            return new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String loadConverter(String id) throws IOException {
        String url = "https://www.320youtube.com/v27/watch?v=" + id; //downloading api
        byte[] bytes = load(url, null);
        return new String(bytes);
    }

    private String getID(String youtubeUrl) {
        Matcher m = VID_ID_PATTERN.matcher(youtubeUrl);
        if (!m.find()) {
            throw new IllegalArgumentException("Invalid YouTube URL.");
        }
        return m.group();
    }

    private String getMP3URL(String converter) {
        Matcher m = MP3_URL_PATTERN.matcher(converter);
        if (m.find())
            return m.group();

        throw new IllegalArgumentException("Invalid converter");
    }

    private void replaceAll(StringBuilder sb, Pattern pattern, String replacement) {
        Matcher m = pattern.matcher(sb);
        int start = 0;
        while (m.find(start)) {
            sb.replace(m.start(), m.end(), replacement);
            start = m.start() + replacement.length();
        }
    }
}