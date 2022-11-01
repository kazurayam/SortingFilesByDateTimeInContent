package com.kazurayam.study20221030;

import java.io.IOException;
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
public abstract class AbstractPathComparableByDateTime
        extends AbstractPathComparable
        implements IPathComparable {

    private static final Logger logger = LoggerFactory.getLogger(PathComparableByContentEmailDate.class);

    public static final DateTimeFormatter EMAIL_DATE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("dd MMM yyyy HH:mm:ss ")
                    .appendOffset("+HHmm", "+0000")
                    .toFormatter(Locale.ENGLISH);

    protected Path path;
    protected ZonedDateTime timestamp;

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public AbstractPathComparableByDateTime(Path path) {
        super(path);
        this.timestamp = resolveTimestamp(path);
        this.value = this.timestamp.format(DATE_TIME_FORMATTER);
    }

    public ZonedDateTime getTimestamp() { return this.timestamp; }

    abstract ZonedDateTime resolveTimestamp(Path path);

    @Override
    public int compareTo(IPathComparable obj) {
        if (obj instanceof AbstractPathComparableByDateTime) {
            AbstractPathComparableByDateTime other = (AbstractPathComparableByDateTime)obj;
            if (timestamp.isBefore(other.timestamp)) {
                return -1;
            } else if (timestamp.isEqual(other.timestamp)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            throw new IllegalArgumentException("obj is not an instance of " +
                    AbstractPathComparableByDateTime.class.getName());
        }
    }
}

