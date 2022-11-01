package com.kazurayam.study20221030;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathComparableByPathStringTest {

    private static Path dataDir;
    private List<Path> dataFiles;

    @BeforeAll
    public static void beforeClass() {
        dataDir = Paths.get(System.getProperty("user.dir")).resolve("src/test/fixtures/data");
    }

    @BeforeEach
    public void setup() throws IOException {
        dataFiles = Files.list(dataDir).collect(Collectors.toList());
    }

    @Test
    public void test_compareTo() {
        IPathComparable p0 = new PathComparableByPathString(
                        TestHelper.lookup(dataFiles, "79edddc6"));
        IPathComparable p1 = new PathComparableByPathString(
                        TestHelper.lookup(dataFiles, "f503182a"));
        assertTrue(p0.compareTo(p1) < 0,
                String.format("p0.getValue()=%s, p1.getValue()=%s",
                        p0.getValue(),
                        p1.getValue()
                )
        );
    }

}
