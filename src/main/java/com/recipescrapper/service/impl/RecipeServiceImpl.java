package com.recipescrapper.service.impl;

import com.recipescrapper.model.Direction;
import com.recipescrapper.model.Ingredient;
import com.recipescrapper.model.Recipe;
import com.recipescrapper.repository.RecipeRepository;
import com.recipescrapper.service.RecipeService;
import com.recipescrapper.service.visitor.DirectionsNodeVisitor;
import com.recipescrapper.service.visitor.IngredientsNodeVisitor;
import com.recipescrapper.utils.HtmlGetter;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeTraversor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository repository;
    private HtmlGetter getter;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repository, HtmlGetter getter) {
        this.getter = getter;
        this.repository = repository;
    }

    @Override
    public Recipe scrapeUrl(String urlString) throws IOException {
        Recipe recipe = new Recipe();
        Document doc = getter.getDocument(urlString);
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        Element title = doc.select("title").first();
        String name = title.text();
        recipe.setId(RandomStringUtils.randomAlphanumeric(20));

        List<Ingredient> ingredients = findIngredients(doc);
        ingredients.forEach(ingredient ->  {
            ingredient.setRecipe(recipe);
            ingredient.setCreatedAt(now);
            ingredient.setUpdatedAt(now);
        });
        recipe.setIngredients(ingredients);

        List<Direction> directions = findDirections(doc);
        directions.forEach(direction -> {
            direction.setRecipe(recipe);
            direction.setCreatedAt(now);
            direction.setUpdatedAt(now);
        });
        recipe.setDirections(directions);

        recipe.setDuration(30);
        recipe.setName(name);
        recipe.setCreatedAt(now);
        recipe.setUpdatedAt(now);
        repository.save(recipe);

        return recipe;
    }

    private List<Direction> findDirections(Document doc) {
        List<Direction> store = new ArrayList<>();
        DirectionsNodeVisitor visitor = new DirectionsNodeVisitor(store);
        Elements elements = doc.children();
        NodeTraversor.traverse(visitor, elements);

        return store;
    }
    private List<Ingredient> findIngredients(Document doc) {
        List<Ingredient> store = new ArrayList<>();
        IngredientsNodeVisitor visitor = new IngredientsNodeVisitor(store);
        Elements elements = doc.children();
        NodeTraversor.traverse(visitor, elements);

        return store;
    }
}
