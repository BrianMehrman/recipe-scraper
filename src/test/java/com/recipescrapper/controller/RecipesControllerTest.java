package com.recipescrapper.controller;

import com.recipescrapper.model.Recipe;
import com.recipescrapper.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(RecipesController.class)
public class RecipesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Test
    public void scrapeRecipe() throws Exception {
        String url = "https://www.foodnetwork.com/recipes/food-network-kitchen/one-pot-cajun-chicken-pasta-8051436";
        String body = "{\"url\":\"https://www.foodnetwork.com/recipes/food-network-kitchen/one-pot-cajun-chicken-pasta-8051436\"}";
        Recipe recipe = new Recipe();
        when(recipeService.scrapeUrl(url)).thenReturn(recipe);
        mockMvc.perform(post("/v1/recipes/scrape")
                .contentType(MediaType.valueOf("application/json")).content(body))
                .andExpect(status().isOk());

    }
}
