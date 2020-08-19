package com.recipescrapper.service.visitor;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.util.List;

public class DirectionsNodeVisitor implements NodeVisitor {

    private final String regex = "(?i)direction[s]*|instruction[s]*";
    private List<String> store;
    private Elements visited = new Elements();

    public DirectionsNodeVisitor(List<String> store) { this.store = store; }

    @Override
    public void head(Node node, int depth) {
        if (node instanceof Element) {
            Element tag = (Element) node;
            String innerText = tag.ownText();
            // check to see if the inner text matches what we consider ingredients
            if(innerText.matches(regex)) {
                Element parent = tag.parent();
                if (!visited.contains(parent)) {
                    visited.add(parent);
                    List<String> directions = parent.select("li").eachText();
                    store.addAll(directions);
                }
            }
        }
    }

    @Override
    public void tail(Node node, int depth) {

    }
}
