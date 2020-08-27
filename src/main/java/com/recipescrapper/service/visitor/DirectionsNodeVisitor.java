package com.recipescrapper.service.visitor;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirectionsNodeVisitor implements NodeVisitor {

    private final String regex = "(?i)direction[s]*|instruction[s]*";
    private List<String> store;
    private Elements visited = new Elements();
    private Boolean directionsFound;

    public DirectionsNodeVisitor(List<String> store) {
        this.store = store;
        this.directionsFound = false;
    }

    @Override
    public void head(Node node, int depth) {
        if (node instanceof Element) {
            Element tag = (Element) node;
            String innerText = tag.ownText();
            // check to see if the inner text matches what we consider ingredients
            if(innerText.matches(regex)) {
                // found ingredient header, now we must walk up the tree to find the ingredients
                visited.add(tag);
                walkUpTree(tag);
            }
        }
    }

    private void walkUpTree(Element element) {
        for(Element parent : element.parents()) {
            // walk up tree until we find an element with more text than elements ownText
            if(!directionsFound && !visited.contains(parent) && parent.text() != element.ownText()) {
                walkDownTree(parent, element.ownText());
                visited.add(parent);
            }
        }
    }

    private void walkDownTree(Element element, String headerText) {
        if (!visited.contains(element) && element.hasText() && element.childrenSize() > 0) {
            for(Element e : element.children()) {
                if (!visited.contains(e)) {
                    if (e.hasText() && !e.text().contains(headerText)) {
                        addText(e);
                    } else if (e.hasText()) {
                        walkDownTree(e, headerText);
                    }
                    visited.add(e);
                }
            }
        }
    }

    private void addText(Element e) {
        Elements elements = e.select("li,p");

        List<String> list = elements.eachText();
        List<String> directions = list.stream()
                .filter(str -> !str.isBlank())
                .collect(Collectors.toList());
        store.addAll(directions);
        directionsFound = true;
    }

    @Override
    public void tail(Node node, int depth) {

    }
}
