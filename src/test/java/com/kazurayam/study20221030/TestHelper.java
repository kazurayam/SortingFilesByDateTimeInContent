package com.kazurayam.study20221030;

import java.nio.file.Path;
import java.util.List;

public class TestHelper {

    public static Path lookup(List<Path> paths, String pattern) {
        for (int i = 0; i < paths.size(); i++) {
            Path p = paths.get(i);
            if (p.getFileName().toString().startsWith(pattern)) {
                return p;
            }
        }
        return null;
    }
}
