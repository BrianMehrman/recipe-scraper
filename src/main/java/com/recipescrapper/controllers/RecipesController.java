package com.recipescrapper.controllers;

import com.recipescrapper.models.Recipe;
import com.recipescrapper.models.params.RecipeScrapeParams;
import com.recipescrapper.services.RecipeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/recipes/")
public class RecipesController {

    private RecipeServiceImpl service;

    @Autowired
    public RecipesController(RecipeServiceImpl service) {
        this.service = service;
    }

    @PostMapping(value = "/scrape", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> scrapeRecipe(@RequestBody RecipeScrapeParams params) throws IOException {
        String url = params.getUrl();
        Recipe recipe = service.scrapeUrl(url);
        return ResponseEntity.ok(recipe);
    }
}
