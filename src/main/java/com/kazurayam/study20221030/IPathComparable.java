package com.kazurayam.study20221030;

import java.nio.file.Path;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * defines compareTo() by the value returned by getValue()
 */
public interface IPathComparable extends Comparable<IPathComparable> {

    ZonedDateTime UNIX_EPOCH_SINCE =
            ZonedDateTime.of(1970, 1, 1,
                    0, 0, 0, 0,
                    ZoneId.of("UTC"));

    public Path get();

    public String getValue();

    default int compareTo(IPathComparable other) {
        return this.getValue().compareTo(other.getValue());
    }

}
