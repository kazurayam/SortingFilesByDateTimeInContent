package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This demonstrates how to sort Path objects
 * by the default comparator (by the path string)
 */
public class Main0 {

    private Path dir;

    public Main0() { dir = null; }

    public void setDir(Path dir) throws IOException {
        Objects.requireNonNull(dir);
        if (!Files.exists(dir)) {
            throw new IOException(dir + " is not present");
        }
        if (!Files.isDirectory(dir)) {
            throw new IOException(dir + " is not a directory");
        }
        this.dir = dir;
    }

    public void execute() throws IOException {
        if (this.dir == null) {
            throw new IllegalStateException(
                    "dir is not set. you should do setDir(Path) before execute()");
        }
        // sort the files in the give directory by its lastModified timestamp
        List<Path> files =
                Files.list(this.dir)
                        .filter(p -> { return p.getFileName().toString().endsWith(".eml"); })
                        .map(p -> { return new PathComparableByPathString(p);})
                        // sort the Path objects by its path string
                        .sorted(Comparator.reverseOrder())
                        .map(p -> { return p.get(); })
                        .collect(Collectors.toList());

        int count = 0;
        for (Path p : files) {
            count += 1;
            System.out.println(String.format("%d\t%s",
                    count, dir.relativize(p)));
        }
    }
}
