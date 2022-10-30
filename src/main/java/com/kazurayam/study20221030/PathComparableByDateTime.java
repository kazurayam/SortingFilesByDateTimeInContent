package com.kazurayam.study20221030;

import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A wrapper for Path, that enables comparison
 * by the lastModified property of File
 */
public abstract class PathComparableByDateTime
        implements Comparable<PathComparableByDateTime> {

    private static final Logger logger = LoggerFactory.getLogger(PathComparableByContentEmailDate.class);

    protected Path path;
    protected ZonedDateTime timestamp;

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public PathComparableByDateTime(Path path) {
        this.path = path;
        this.timestamp = resolveTimestamp(path);
    }

    public Path get() {
        return this.path;
    }

    public ZonedDateTime getTimestamp() { return this.timestamp; }

    public String getTimestampFormatted() {
        return timestamp.format(DATE_TIME_FORMATTER);
    }

    abstract ZonedDateTime resolveTimestamp(Path path);


    @Override
    public int compareTo(PathComparableByDateTime other) {
        if (timestamp.isBefore(other.timestamp)) {
            return -1;
        } else if (timestamp.isEqual(other.timestamp)) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return path.toString();
    }
}

