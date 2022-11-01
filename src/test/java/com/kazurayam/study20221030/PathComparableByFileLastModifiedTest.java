package com.kazurayam.study20221030;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathComparableByFileLastModifiedTest {

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
    public void test_getValue_isDateTime() {
        PathComparableByFileLastModified instance =
                new PathComparableByFileLastModified(
                        TestHelper.lookup(dataFiles, "79edddc6"));
        String value = instance.getValue();
        assertTrue(value.matches(PATTERN),
                String.format("value=%s is not in the yyyyMMdd_hhmmss format", value));
    }

    private static String PATTERN = "\\d{8}_\\d{6}";

    @Test
    public void test_String_matches() {
        assertTrue("20221102_065421".matches(PATTERN));
    }

    @Test
    public void test_compareTo() {
        IPathComparable p0 =
                new PathComparableByFileLastModified(
                        TestHelper.lookup(dataFiles, "79edddc6"));
        IPathComparable p1 =
                new PathComparableByFileLastModified(
                        TestHelper.lookup(dataFiles, "f503182a"));
        assertTrue(p0.compareTo(p1) < 0,
                String.format("p0.getValue()=%s, p1.getValue()=%s",
                        p0.getValue(),
                        p1.getValue()
                )
        );
    }
}
