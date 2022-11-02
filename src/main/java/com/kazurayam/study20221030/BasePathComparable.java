package com.kazurayam.study20221030;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

/**
 * Abstract class that implements common methods and properties for other concrete PathParable* classes
 */
public abstract class BasePathComparable implements IPathComparable {

    private static final Logger logger = LoggerFactory.getLogger(BasePathComparable.class);

    protected Path path;
    protected String value;

    public BasePathComparable(Path path) {
        this.path = path;
        this.value = path.toString();
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Path get() { return this.path; }

}
