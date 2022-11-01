package com.kazurayam.study20221030;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.time.ZonedDateTime;

public abstract class PathComparable implements Comparable<PathComparable> {

    private static final Logger logger = LoggerFactory.getLogger(PathComparable.class);

    protected Path path;
    protected String value;

    public PathComparable(Path path) {
        this.path = path;
        this.value = getValue();
    }

    protected String getValue() {
        return path.toString();
    }

    public Path get() { return this.path; }

    @Override
    public int compareTo(PathComparable other) {
        return this.getValue().compareTo(other.getValue());
    }
}
