package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

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
public final class PathComparableByContentEmailDate
        extends AbstractPathComparableByDateTime
        implements IPathComparableByContent {

    private static final Logger logger = LoggerFactory.getLogger(PathComparableByContentEmailDate.class);

    public static final ZonedDateTime UNIX_EPOCH_SINCE =
            ZonedDateTime.of(1970, 1, 1,
                    0, 0, 0, 0,
                    ZoneId.of("UTC"));

    public PathComparableByContentEmailDate(Path path) {
        super(path);
        this.timestamp = resolveTimestamp(path);
        this.value = this.timestamp.format(AbstractPathComparableByDateTime.DATE_TIME_FORMATTER);
    }

    @Override
    public ZonedDateTime resolveTimestamp(Path path) {
        Objects.requireNonNull(path);
        if (!Files.exists(path)) {
            logger.warn(path.toString() + " is not found. Will return UNIX_EPOCH_SINCE");
            return UNIX_EPOCH_SINCE;
        }
        Map<String, String > headers = parseContentForHeaders(path);
        if (headers.keySet().size() > 0) {
            ZonedDateTime dt = getContentDate(headers);
            return dt;
        } else {
            logger.warn(path + " contains 0 Email headers");
            return UNIX_EPOCH_SINCE;
        }
    }

    static ZonedDateTime getContentDate(Map<String, String> headers) {
        if (headers.containsKey("Date")) {
            return ZonedDateTime.parse(headers.get("Date"), EMAIL_DATE_FORMATTER);
        } else {
            return UNIX_EPOCH_SINCE;
        }
    }

}