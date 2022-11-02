package com.kazurayam.study20221030;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathComparableByContentEmailHeaderValue
        extends BasePathComparable
        implements IPathComparableByContent, IPathComparableByDateTime {

    private static final Logger logger = LoggerFactory.getLogger(PathComparableByContentEmailHeaderValue.class);

    private ZonedDateTime timestamp;

    public PathComparableByContentEmailHeaderValue(Path path, String headerKey) {
        super(path);
        Objects.requireNonNull(headerKey);
        this.timestamp = resolveTimestamp(path);
        if (headerKey.equals("Date")) {
            this.value = this.timestamp.format(IPathComparableByDateTime.SORTABLE_DATETIME_FORMATTER);
        } else {
            Map<String, String> headers = parseContentForHeaders(path);
            if (headers.keySet().size() > 0) {
                if (headers.containsKey(headerKey)) {
                    this.value = headers.get(headerKey);
                } else {
                    logger.warn(String.format("%s does not contain the key %s", this.path, headerKey));
                    this.value = "";
                }
            } else {
                logger.warn(path + " contains no Email headers");
                this.value = "";
            }
        }
    }

    @Override
    public ZonedDateTime resolveTimestamp(Path path) {
        Objects.requireNonNull(path);
        if (!Files.exists(path)) {
            logger.warn(path.toString() + " is not found. Will return UNIX_EPOCH_SINCE");
            return UNIX_EPOCH_SINCE;
        }
        Map<String, String> headers = parseContentForHeaders(path);
        if (headers.keySet().size() > 0) {
            ZonedDateTime dt = getDateInEmailHeader(headers);
            return dt;
        } else {
            logger.warn(path + " contains 0 Email headers");
            return UNIX_EPOCH_SINCE;
        }
    }

    static ZonedDateTime getDateInEmailHeader(Map<String, String> headers) {
        if (headers.containsKey("Date")) {
            return ZonedDateTime.parse(headers.get("Date"), EMAIL_DATE_FORMATTER);
        } else {
            return UNIX_EPOCH_SINCE;
        }
    }

}
