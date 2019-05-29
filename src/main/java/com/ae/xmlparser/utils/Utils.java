package com.ae.xmlparser.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Utils {
    private static final String CHARSET_NAME = "utf8";
    private static final String DEFAULT_ID = "make-everything-ok-button";

    private static Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    private Utils(){}

    public static String getElementIdToSearch(String[] args) {
        if (args.length > 2) {
            return args[3];
        } else {
            return DEFAULT_ID;
        }
    }

    public static Optional<Document> getJsoupDocument(String filePath) {
        try {
            return Optional.of(Jsoup.parse(new File(filePath), CHARSET_NAME));
        } catch (IOException e) {
            LOGGER.error("File not found: " + e.getMessage());
            return Optional.empty();
        }
    }
}
