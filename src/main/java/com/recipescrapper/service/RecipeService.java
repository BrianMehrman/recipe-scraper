package com.recipescrapper.service;

import com.recipescrapper.model.Recipe;

import java.io.IOException;

public interface RecipeService {
    Recipe scrapeUrl(String url) throws IOException;
}
