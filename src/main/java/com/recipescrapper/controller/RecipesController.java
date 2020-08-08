package com.recipescrapper.controller;

import com.recipescrapper.model.Recipe;
import com.recipescrapper.model.param.RecipeScrapeParams;
import com.recipescrapper.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/v1/recipes/")
public class RecipesController {

    private final RecipeService recipeService;

    @Autowired
    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping(value = "/scrape", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity scrapeRecipe(@RequestBody RecipeScrapeParams params) throws IOException {
        String url = params.getUrl();
        Recipe recipe = recipeService.scrapeUrl(url);
        return ResponseEntity.ok(recipe);
    }
}
