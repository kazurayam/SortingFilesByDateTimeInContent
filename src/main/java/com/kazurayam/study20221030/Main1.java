package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main1 {

    private Path dir;

    public Main1() {
        dir = null;
    }

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
        List<PathComparableByDateTime> files =
                Files.list(this.dir)
                        .filter(p -> { return p.getFileName().toString().endsWith(".eml"); })
                        .map(PathComparableByFileLastModified::new)
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList());

        int count = 0;
        for (PathComparableByDateTime pc : files) {
            count += 1;
            System.out.println(String.format("%d\t%s\t%s",
                    count, pc.getTimestampFormatted(), pc.get()));
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException("usage: java -jar xxx <path of data directory>");
        }
        Path p = Paths.get(args[0]);
        Main1 instance = new Main1();
        instance.setDir(p);
        instance.execute();
    }
}
