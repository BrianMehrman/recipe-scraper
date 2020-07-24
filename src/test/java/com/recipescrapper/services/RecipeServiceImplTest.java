package com.recipescrapper.services;

import com.recipescrapper.models.Recipe;
import com.recipescrapper.models.repositories.RecipeRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.io.IOException;
import java.util.List;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class RecipeServiceImplTest {

    private RecipeService service;

    @Autowired
    private EntityManager em;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        service = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void whenScrapeUrl_aRecipeIsCreated() throws IOException {
        // TODO: mock out the html call
        String url = "https://www.staceyhomemaker.com/buffalo-cauliflower-tacos/";
        Recipe recipe = service.scrapeUrl(url);

        Query query = em.createNativeQuery("SELECT * FROM recipes", Tuple.class);
        List<Tuple> results = query.getResultList();
        Assert.assertEquals("Bomb Ass Buffalo Cauliflower Tacos", recipe.getName());
    }
}