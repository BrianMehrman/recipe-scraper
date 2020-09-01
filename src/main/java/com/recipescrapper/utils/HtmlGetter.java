package com.recipescrapper.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HtmlGetter {

    public Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        return document;
    }
}
