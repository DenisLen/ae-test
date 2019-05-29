package com.ae.xmlparser.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePathParser {
    private static Logger LOGGER = LoggerFactory.getLogger(FilePathParser.class);
    private Path originFilePath;
    private Path diffFilePath;

    private static final String DEFAULT_PATH_TO_ORIGIN_SAMPLE = "./src/main/resources/sample-0-origin.html";
    private static final String DEFAULT_PATH_TO_SAMPLE_WITH_DIFFERENCES = "./src/main/resources/sample-0-origin.html";

    public FilePathParser(String[] args) {
        if (args.length >= 2) {
            originFilePath = Paths.get(args[0]);
            diffFilePath = Paths.get(args[1]);
        } else {
            originFilePath = Paths.get(DEFAULT_PATH_TO_ORIGIN_SAMPLE);
            diffFilePath = Paths.get(DEFAULT_PATH_TO_SAMPLE_WITH_DIFFERENCES);
        }
    }

    public Path getOriginFilePath() {
        LOGGER.info("Using file path to origin sample : " + originFilePath.toString());
        return originFilePath;
    }

    public Path getDiffFilePath() {
        LOGGER.info("Using file path to sample with difference: " + originFilePath.toString());
        return diffFilePath;
    }
}
