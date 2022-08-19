package com.recipescrapper.service;

import com.recipescrapper.model.Recipe;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface RecipeService {
    Recipe scrapeUrl(String url) throws IOException;
}
