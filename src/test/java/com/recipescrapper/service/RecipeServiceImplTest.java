package com.recipescrapper.service;

import com.recipescrapper.model.Direction;
import com.recipescrapper.model.Ingredient;
import com.recipescrapper.model.Recipe;
import com.recipescrapper.repository.RecipeRepository;
import com.recipescrapper.service.impl.RecipeServiceImpl;
import com.recipescrapper.utils.HtmlGetter;
import org.cactoos.text.TextOf;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@RunWith(SpringRunner.class)
@PrepareForTest({Jsoup.class})
public class RecipeServiceImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private RecipeRepository repository;

    private RecipeServiceImpl mockSite(final String filePath) throws IOException {
        final String htmlString = new TextOf(new File(filePath)).asString();
        final Document html = Jsoup.parse(htmlString);

        final HtmlGetter getter = mock(HtmlGetter.class);
        Mockito.when(getter.getDocument(any(String.class))).thenReturn(html);
        final RecipeServiceImpl service = new RecipeServiceImpl(repository, getter);

        return service;
    }

    @Test
    public void whenExcute_scrapsStaceyHomemakeSite() throws IOException {
        final String url = "https://www.staceyhomemaker.com/buffalo-cauliflower-tacos/";
        final RecipeServiceImpl service = mockSite("src/test/resources/recipes/bomb-ass-sample.html");
        final Recipe recipe = service.scrapeUrl(url);
        final String name = recipe.getName();
        final List<Ingredient> ingredients = recipe.getIngredients();
        final List<Direction> directions = recipe.getDirections();
        Assert.assertEquals("Bomb Ass Buffalo Cauliflower Tacos", name);

        Assert.assertEquals(13, ingredients.size());
        Assert.assertEquals(6, directions.size());
    }

    @Test
    public void whenExcute_scrapsFoodNetworkSite() throws IOException {
        final String url = "https://www.foodnetwork.com/recipes/food-network-kitchen/jambalaya-3362212";
        final RecipeServiceImpl service = mockSite("src/test/resources/recipes/jambalaya-recipe.html");
        final Recipe recipe = service.scrapeUrl(url);
        final String name = recipe.getName();
        final List<Ingredient> ingredients = recipe.getIngredients();
        final List<Direction> directions = recipe.getDirections();

        Assert.assertEquals("Jambalaya Recipe | Food Network Kitchen | Food Network", name);
        Assert.assertEquals(13, ingredients.size());
        Assert.assertEquals(4, directions.size());
    }

    @Test
    public void whenExecute_scrapesRecipeChildren() throws IOException {
        final String url = "https://www.foodsite.com/recipes/recipe-children";
        final RecipeServiceImpl service = mockSite("src/test/resources/recipes/recipe-children.html");
        final Recipe recipe = service.scrapeUrl(url);
        final String name = recipe.getName();
        final List<Ingredient> ingredients = recipe.getIngredients();
        final List<Direction> directions = recipe.getDirections();

        Assert.assertEquals("Chicken Soup", name);
        Assert.assertEquals(7, ingredients.size());
        Assert.assertEquals(5, directions.size());
    }

}
