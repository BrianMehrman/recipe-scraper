package com.recipescrapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;

@Configuration
public class RecipePatternStore {
    @Bean
    public Hashtable<String, String[]> classNameTable() {
        return new Hashtable<>();
    }
}
