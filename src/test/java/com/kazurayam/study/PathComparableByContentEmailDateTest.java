package com.kazurayam.study;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathComparableByContentEmailDateTest {


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
        DateTimeFormatter dtf = PathComparableByContentEmailDate.EMAIL_DATE_FORMATTER;
        String dateStr = "26 Oct 2022 12:00:00 -0700";
        TemporalAccessor ta = dtf.parse(dateStr);
    }


    @Test
    public void test_compareTo() {
        PathComparableByDateTime p0 = new PathComparableByContentEmailDate(dataFiles.get(0));
        PathComparableByDateTime p1 = new PathComparableByContentEmailDate(dataFiles.get(1));
        assertEquals(0, p0.compareTo(p1),
                String.format("p0.timestamp=%s, p1.timestamp=%s",
                        p0.getTimestampFormatted(),
                        p1.getTimestampFormatted()));
    }
}
