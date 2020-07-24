package com.recipescrapper.services;

import com.recipescrapper.models.Recipe;
import com.recipescrapper.models.params.RecipeScrapeParams;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface RecipeService {
    Recipe scrapeUrl(String url) throws IOException;
}
