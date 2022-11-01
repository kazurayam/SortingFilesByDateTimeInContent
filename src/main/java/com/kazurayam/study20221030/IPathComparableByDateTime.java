package com.kazurayam.study20221030;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class that wraps a Path object and enables comparison
 * by its ZonedDateTime.
 */
public interface IPathComparableByDateTime {

    DateTimeFormatter EMAIL_DATE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd MMM yyyy HH:mm:ss ")
                    .appendOffset("+HHmm", "+0000")
                    .toFormatter(Locale.ENGLISH);

    DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    ZonedDateTime resolveTimestamp(Path path);
}

