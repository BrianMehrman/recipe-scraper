package com.recipescrapper.service.visitor;

import com.recipescrapper.model.Ingredient;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.util.List;
import java.util.stream.Collectors;

// food network
// /html/body/section/div[3]/div[3]/div/div[2]/div[1]/div/section/div[2]/div[1]/section/section/header/div/h3/span
// /html/body/section/div[3]/div[3]/div/div[2]/div[1]/div/section/div[2]/div[1]/section/section/div[1]/p[1]

// allrecipes
// /html/body/div[1]/div/main/div[1]/div[2]/div[1]/div[2]/div[2]/div[4]/section[1]/div[1]/h2
// /html/body/div[1]/div/main/div[1]/div[2]/div[1]/div[2]/div[2]/div[4]/section[1]/fieldset/ul/li[1]/label/span/span
// /html/body/div[1]/div/main/div[1]/div[2]/div[1]/div[2]/div[2]/div[4]/section[1]/fieldset/ul/li[2]/label/span/span


public class IngredientsNodeVisitor implements NodeVisitor {

    private final String regex = "(?i)ingredient[s]*";
    private List<Ingredient> store;
    private Elements visited = new Elements();
    private Boolean ingredientsFound;

    public IngredientsNodeVisitor(List<Ingredient> store) {
        this.store = store;
        this.ingredientsFound = false;
    }

    @Override
    public void head(Node node, int depth) {
        /*
        * Need to loop through the page and find a node where the siblings all have the same class
        * */

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
            if(!ingredientsFound && !visited.contains(parent) && parent.text() != element.ownText()) {
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
        List<Ingredient> ingredients = list.stream()
                .filter(str -> !str.isBlank())
                .map(str -> new Ingredient(str))
                .collect(Collectors.toList());
        store.addAll(ingredients);
        ingredientsFound = true;
    }

    @Override
    public void tail(Node node, int depth) {

    }
}
