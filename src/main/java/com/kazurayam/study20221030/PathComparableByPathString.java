package com.kazurayam.study20221030;


import java.nio.file.Path;

/**
 * Path is compared by its string value as full path
 */
public final class PathComparableByPathString extends AbstractPathComparable {

    public PathComparableByPathString(Path path) {
        super(path);
    }
}
