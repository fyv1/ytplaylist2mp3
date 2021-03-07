package pl.fyv.ytdownloader.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Downloader {
    private static final Pattern VID_ID_PATTERN = Pattern.compile("(?<=v\\=|youtu\\.be\\/)\\w+");
    private static final Pattern MP3_URL_PATTERN = Pattern.compile("https\\:\\/\\/\\w+\\.ytapivmp3\\.com\\/download.+?(?=\\\")");
    private static final Pattern FILENAME_PATTERN = Pattern.compile("(?<=filename=\").+?(?=\")");
    public static File youtubeToMP3(String youtubeUrl, String path) throws IOException {
        String id = getID(youtubeUrl);
        String converter = loadConverter(id);
        String mp3url = getMP3URL(converter);

        StringBuilder builder = new StringBuilder();
        byte[] mp3 = load(mp3url, builder);

        File output = new File(path+builder.toString());
        FileOutputStream stream = new FileOutputStream(output);
        stream.write(mp3);
        stream.flush();
        stream.close();

        return output;
    }

    private static byte[] load(String url, StringBuilder filename) throws IOException {
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
        }
        System.out.println("xd "+filename);

        InputStream is = connection.getInputStream();
        byte[] byteChunk = new byte[2500];
        int n;

        while ((n = is.read(byteChunk)) > 0) {
            baos.write(byteChunk, 0, n);
        }
        connection.disconnect();
        baos.flush();
        baos.close();

        return baos.toByteArray();
    }

    private static String toUTF8(String s) {
        try {
            String s1 = new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8.name());
            System.out.println(s+", "+s1);
            return s1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadConverter(String id) throws IOException {
        String url = "https://www.320youtube.com/v14/watch?v=" + id;
        byte[] bytes = load(url, null);
        return new String(bytes);
    }

    private static String getID(String youtubeUrl) {
        Matcher m = VID_ID_PATTERN.matcher(youtubeUrl);
        if (!m.find()) {
            throw new IllegalArgumentException("Invalid YouTube URL.");
        }
        return m.group();
    }

    private static String getMP3URL(String converter) {
        Matcher m = MP3_URL_PATTERN.matcher(converter);
        if (m.find())
            return m.group();

        throw new IllegalArgumentException("Invalid converter");
    }
}