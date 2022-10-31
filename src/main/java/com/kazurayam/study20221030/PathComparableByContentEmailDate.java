package com.kazurayam.study20221030;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper for Path, that enables comparing 2 Paths
 * by the "Date" property contained in each file.
 * For example,
 ```
 X-Sender: "Do Not Reply" donotreply@anywhere.com
 X-Receiver: xyz.com
 MIME-Version: 1.0
 From: "Do Not Reply" donotreply@anywhere.com
 To: xyz.com
 Date: 26 Oct 2022 14:43:15 -0700
 Subject: Hello world, what is my URL?
 Content-Type: text/html; charset=utf-8
 Content-Transfer-Encoding: base64
 aHR0cHM6Ly93d3cuZ29vZ2xlLmNvbQ==
 ```
 * If the file does not contain a "Date" property, it will be regarded to have
 * midnight, January 1, 1970 of your local time zone.
 */
public final class PathComparableByContentEmailDate extends PathComparableByDateTime {

    private static final Logger logger = LoggerFactory.getLogger(PathComparableByContentEmailDate.class);

    public static final DateTimeFormatter EMAIL_DATE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd MMM yyyy HH:mm:ss ")
                    .appendOffset("+HHmm", "+0000")
                    .toFormatter(Locale.ENGLISH);

    public PathComparableByContentEmailDate(Path path) {
        super(path);
        this.timestamp = resolveTimestamp(path);
    }

    @Override
    public ZonedDateTime resolveTimestamp(Path path) {
        Map<String, String > headers = parseContentForHeaders(this.path);
        if (headers.keySet().size() > 0) {
            ZonedDateTime dt = getContentDate(headers);
            return dt;
        } else {
            logger.warn(path + " contains 0 Email headers");
            return ZonedDateTime.of(1970, 1, 1,
                    0,0,0,0,
                    ZoneId.of("UTC"));
        }
    }

    static Map<String, String> parseContentForHeaders(Path p) {
        Map<String, String> m = new LinkedHashMap<>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            Files.newInputStream(p.toFile().toPath()),
                            StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null && line.trim().length() != 0) {
                if (line.indexOf(":") >= 0) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String val = line.substring(line.indexOf(":") + 1).trim();
                    m.put(key, val);
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return m;
    }

    static ZonedDateTime getContentDate(Map<String, String> headers) {
        if (headers.containsKey("Date")) {
            return ZonedDateTime.parse(headers.get("Date"), EMAIL_DATE_FORMATTER);
        } else {
            LocalDateTime ldt = LocalDateTime.of(1970, 1, 1, 0, 0, 0);
            return ZonedDateTime.of(ldt, ZoneId.systemDefault());
        }
    }

    @Override
    public int compareTo(PathComparableByDateTime other) {
        if (timestamp.isBefore(other.timestamp)) {
            return -1;
        } else if (timestamp.isEqual(other.timestamp)) {
            return this.get().toString().compareTo(other.get().toString());
        } else {
            return 1;
        }
    }
}