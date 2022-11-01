package com.kazurayam.study20221030;

import java.nio.file.Path;

/**
 * defines compareTo() by the value returned by getValue()
 */
public interface IPathComparable extends Comparable<IPathComparable> {

    public Path get();

    public String getValue();

    default int compareTo(IPathComparable other) {
        return this.getValue().compareTo(other.getValue());
    }

}
