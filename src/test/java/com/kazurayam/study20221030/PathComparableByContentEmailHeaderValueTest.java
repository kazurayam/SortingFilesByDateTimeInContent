package com.kazurayam.study20221030;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathComparableByContentEmailHeaderValueTest {


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
    public void test_EMAIL_DATE_FORMATTER() {
        DateTimeFormatter dtf = IPathComparableByDateTime.EMAIL_DATE_FORMATTER;
        String dateStr = "26 Oct 2022 12:00:00 -0700";
        TemporalAccessor ta = dtf.parse(dateStr);
    }

    @Test
    public void test_EMAIL_DATE_FORMATTER_more() {
        DateTimeFormatter dtf = IPathComparableByDateTime.EMAIL_DATE_FORMATTER;
        String dateStr = "2 Nov 2022 15:32:04 -0700";
        TemporalAccessor ta = dtf.parse(dateStr);
    }


    @Test
    public void test_compareTo_greater_than() {
        IPathComparable p0 =
                new PathComparableByContentEmailHeaderValue(
                        TestHelper.lookup(dataFiles, "79edddc6"), "Date");
        IPathComparable p1 =
                new PathComparableByContentEmailHeaderValue(
                        TestHelper.lookup(dataFiles, "f503182a"), "Date");
        assertTrue(p0.compareTo(p1) > 0,
                String.format("p0.getValue()=%s, p1.getValue()=%s",
                        p0.getValue(),
                        p1.getValue()
                )
        );
    }

    @Test
    public void test_compareTo_equal() {
        IPathComparable p0 =
                new PathComparableByContentEmailHeaderValue(
                        TestHelper.lookup(dataFiles, "79edddc6"), "Date");
        assertEquals(0, p0.compareTo(p0),
                String.format("p0.getValue()=%s, p0.getValue()=%s",
                        p0.getValue(),
                        p0.getValue()
                )
        );
    }

    @Test
    public void test_compareTo_less_than() {
        IPathComparable p0 =
                new PathComparableByContentEmailHeaderValue(
                        TestHelper.lookup(dataFiles, "79edddc6"), "Date");
        IPathComparable p1 =
                new PathComparableByContentEmailHeaderValue(
                        TestHelper.lookup(dataFiles, "f503182a"), "Date");
        assertTrue( p1.compareTo(p0) < 0,
                String.format("p1.getValue()=%s, p0.getValue()=%s",
                        p1.getValue(),
                        p0.getValue()
                )
        );
    }
}
