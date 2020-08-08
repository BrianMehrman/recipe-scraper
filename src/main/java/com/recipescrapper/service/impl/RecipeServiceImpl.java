package com.recipescrapper.service.impl;

import com.recipescrapper.model.Recipe;
import com.recipescrapper.repository.RecipeRepository;
import com.recipescrapper.service.RecipeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository repository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository repository) { this.repository = repository; }

    @Override
    public Recipe scrapeUrl(String urlString) throws IOException {
        Recipe recipe = new Recipe();
        Document doc;
        try {
            doc = Jsoup.connect(urlString).get();
            Element title = doc.select("title").first();

            String name = title.text();
            recipe.setId(RandomStringUtils.randomAlphanumeric(20));
            recipe.setDuration(30);
            recipe.setName(name);

            Timestamp now = Timestamp.valueOf(LocalDateTime.now());

            recipe.setCreatedAt(now);
            recipe.setUpdatedAt(now);
            repository.save(recipe);
        } catch (HttpStatusException e) {
            System.out.println("Could not connect to webpage");
        }

        return recipe;
    }
}
