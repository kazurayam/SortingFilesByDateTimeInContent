package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class PathComparableByFileLastModified extends PathComparableByDateTime {

    public PathComparableByFileLastModified(Path p) {
        super(p);
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
