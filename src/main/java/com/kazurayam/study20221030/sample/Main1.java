package com.kazurayam.study20221030.sample;

import com.kazurayam.study20221030.IPathComparable;
import com.kazurayam.study20221030.PathComparableByFileLastModified;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This demonstrates how to use PathComparableByFileLastModified class
 */
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
        List<IPathComparable> files =
                Files.list(this.dir)
                        .filter(p -> { return p.getFileName().toString().endsWith(".eml"); })
                        // wrap the path
                        .map(PathComparableByFileLastModified::new)
                        // to sort by the lastModified property of File
                        .sorted(Comparator.reverseOrder())
                        .collect(Collectors.toList());

        int count = 0;
        for (IPathComparable pc : files) {
            count += 1;
            assert pc != null;
            assert dir != null;
            assert pc.get() != null;
            System.out.printf("%d\t%s\t%s%n",
                    count, pc.getValue(), this.dir.relativize(pc.get()));
        }
    }

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalArgumentException(
                    "usage: java " + Main1.class.getName() + " <path of data directory>");
        }
        Path p = Paths.get(args[0]);
        Main1 instance = new Main1();
        instance.setDir(p);
        instance.execute();
    }
}
