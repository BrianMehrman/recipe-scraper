package com.recipescrapper.controller;

import com.recipescrapper.model.Recipe;
import com.recipescrapper.model.param.RecipeScrapeParams;
import com.recipescrapper.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/v1/recipes/")
public class RecipesController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping(value = "/scrape", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity scrapeRecipe(@RequestBody RecipeScrapeParams params) throws IOException {
        String url = params.getUrl();
        Recipe recipe = recipeService.scrapeUrl(url);
        return ResponseEntity.ok(recipe);
    }
}
