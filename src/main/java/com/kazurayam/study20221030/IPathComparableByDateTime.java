package com.kazurayam.study20221030;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

/**
 * Abstract class that wraps a Path object and enables comparison
 * by its ZonedDateTime.
 */
public interface IPathComparableByDateTime {

    DateTimeFormatter EMAIL_DATE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("d MMM yyyy HH:mm:ss ")
                    .appendOffset("+HHmm", "+0000")
                    .toFormatter(Locale.ENGLISH);

    DateTimeFormatter SORTABLE_DATETIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    ZonedDateTime resolveTimestamp(Path path);
}

