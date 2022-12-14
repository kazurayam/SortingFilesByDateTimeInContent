package com.kazurayam.study20221030.sample;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Main1Test {

    Path dataDir;

    @BeforeEach
    public void setup() {
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        dataDir = projectDir.resolve("src/test/fixtures/data");
    }

    @Test
    public void test_smoke() throws IOException {
        System.out.println("------------------------");
        System.out.println(Main1Test.class.getName());
        Main1 instance = new Main1();
        instance.setDir(dataDir);
        instance.execute();
    }
}
