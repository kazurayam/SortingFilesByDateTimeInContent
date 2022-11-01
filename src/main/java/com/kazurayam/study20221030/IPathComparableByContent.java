package com.kazurayam.study20221030;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * defines a reusable method to parse a text file content to get
 * the pairs of "Name: Value".
 */
public interface IPathComparableByContent {

    static final Logger logger = LoggerFactory.getLogger(IPathComparableByContent.class);

    default Map<String, String> parseContentForHeaders(Path p) {
        Objects.requireNonNull(p);
        Map<String, String> m = new LinkedHashMap<>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            Files.newInputStream(p.toFile().toPath()),
                            StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null && line.trim().length() != 0) {
                if (line.contains(":")) {
                    String key = line.substring(0, line.indexOf(":")).trim();
                    String val = line.substring(line.indexOf(":") + 1).trim();
                    m.put(key, val);
                }
            }
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }
        return m;
    }
}
