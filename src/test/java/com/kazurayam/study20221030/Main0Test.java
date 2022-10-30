package com.kazurayam.study20221030;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main0Test {

    Path dataDir;

    @BeforeEach
    public void setup() {
        Path projectDir = Paths.get(System.getProperty("user.dir"));
        dataDir = projectDir.resolve("src/test/fixtures/data");
    }

    @Test
    public void test_smoke() throws IOException {
        System.out.println("------------------------");
        System.out.println(Main0Test.class.getName());
        Main0 instance = new Main0();
        instance.setDir(dataDir);
        instance.execute();
    }

}
