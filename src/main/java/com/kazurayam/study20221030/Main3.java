package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

/**
 * Demonstrates how to
 * - sort text files by descending order of the lastModified property of each File
 * - filter text files which contains an Email Header "To: xyx.com" inside it
 *   while ignoring files with other values such as "To: abc.com".
 */
public class Main3 {

    private Path dir;

    public Main3() {
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
        List<IPathComparable> files =
                Files.list(this.dir)
                        .filter(p -> p.getFileName().toString().endsWith(".eml"))
                        // filter files with "To: xyz.com" header
                        .map(p -> new PathComparableByContentEmailHeaderValue(p, "To"))
                        .filter(p -> p.getValue().matches("xyz.com"))
                        // to sort by the lastModified timestamp
                        .map(p -> new PathComparableByFileLastModified(p.get()))
                        .sorted(reverseOrder())
                        .collect(Collectors.toList());

        int count = 0;
        for (IPathComparable pc : files) {
            count += 1;
            System.out.printf("%d\t%s\t%s%n",
                    count, pc.getValue(), dir.relativize(pc.get()));
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "usage: java " + Main3.class.getName() + " <path of data directory>");
        }
        Path p = Paths.get(args[0]);
        Main3 instance = new Main3();
        instance.setDir(p);
        instance.execute();
    }
}
