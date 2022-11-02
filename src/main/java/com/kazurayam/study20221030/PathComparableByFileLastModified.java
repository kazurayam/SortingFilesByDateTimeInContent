package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Path is compared by the time stamp when the file was last modified
 */
public final class PathComparableByFileLastModified
        extends BasePathComparable
        implements IPathComparableByDateTime {

    private ZonedDateTime timestamp;

    public PathComparableByFileLastModified(Path path) {
        super(path);
        this.timestamp = resolveTimestamp(path);
        this.value = IPathComparableByDateTime.SORTABLE_DATETIME_FORMATTER.format(this.timestamp);
    }

    @Override
    public ZonedDateTime resolveTimestamp(Path path) {
        try {
            FileTime fileTime = Files.getLastModifiedTime(path);
            return ZonedDateTime.ofInstant(fileTime.toInstant(), ZoneId.of("UTC"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
