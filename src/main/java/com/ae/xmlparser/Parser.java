package com.ae.xmlparser;

import com.ae.xmlparser.utils.FilePathParser;
import com.ae.xmlparser.utils.Sorter;
import com.ae.xmlparser.utils.Utils;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.ae.xmlparser.utils.Utils.getJsoupDocument;

public class Parser {
    private static Logger LOGGER = LoggerFactory.getLogger(Parser.class);

    public static void main(String[] args) {
        FilePathParser filePathParser = new FilePathParser(args);

        Optional<Document> docWithOriginalElement = getJsoupDocument(filePathParser.getOriginFilePath().toString());
        Optional<Document> docWithElementWithDifference = getJsoupDocument(filePathParser.getDiffFilePath().toString());

        String elementIdToSearch = Utils.getElementIdToSearch(args);

        Element originalElement = docWithOriginalElement.get().getElementById(elementIdToSearch);
        Element diffElementById = docWithElementWithDifference.get().getElementById(elementIdToSearch);

        String elementWithDifferenceXpath;
        if (diffElementById == null) {
            Attributes originalElementAttributes = originalElement.attributes();
            Elements allElementsOfDocWithDifference = docWithElementWithDifference.get().getAllElements();

            Map<Element, Integer> mapSortedByIdenticalAttributeFrequency =
                    Sorter.sortMapByAttributeFrequency(
                            getElementsWithIdenticalElementFrequency(originalElementAttributes, allElementsOfDocWithDifference)
                    );

            Element diffElement = mapSortedByIdenticalAttributeFrequency.entrySet().iterator().next().getKey();

            if (mapSortedByIdenticalAttributeFrequency.get(diffElement) > 0) {
                elementWithDifferenceXpath = getXpathFromDiffDoc(diffElement);
            } else {
                throw new NoSuchElementException("Element not found!");
            }
        } else {
            elementWithDifferenceXpath = getXpathFromDiffDoc(diffElementById);
        }

        LOGGER.info("XPath to found Element: " + elementWithDifferenceXpath);
    }



    private static Map<Element, Integer> getElementsWithIdenticalElementFrequency(Attributes originalAttrs, Elements elements) {
        Map<Element, Integer> elementsWithFrequency = new HashMap<>();

        elements.forEach(element -> {
            AtomicInteger count = new AtomicInteger();
            Attributes diffAttributes = element.attributes();
            originalAttrs.forEach(a -> {
                String originalAttr = a.getKey();
                if (originalAttrs.get(originalAttr).equals(diffAttributes.get(originalAttr))) {
                    count.getAndIncrement();
                }
            });
            elementsWithFrequency.put(element, count.intValue());
        });

        return elementsWithFrequency;
    }

    private static String getXpathFromDiffDoc(Element elementToGetXPathFrom) {
        StringBuilder xPath = new StringBuilder();

        xPath.append("/")
                .append(elementToGetXPathFrom.tagName())
                .append("[")
                .append(elementToGetXPathFrom.elementSiblingIndex() + 1)
                .append("]");

        elementToGetXPathFrom.parents().stream()
                .forEachOrdered(element -> {
                    xPath.insert(0, "/" + element.tagName() + "[" + (element.elementSiblingIndex() + 1) + "]");
                });

        return xPath.toString();
    }
}
