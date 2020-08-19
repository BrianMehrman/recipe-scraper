package com.recipescrapper.service;

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

    private RecipeServiceImpl mockSite(String filePath) throws IOException {
        String htmlString = new TextOf(
                new File(filePath)
        ).asString();
        Document html = Jsoup.parse(htmlString);

        HtmlGetter getter = mock(HtmlGetter.class);
        Mockito.when(getter.getDocument(any(String.class))).thenReturn(html);
        RecipeServiceImpl service = new RecipeServiceImpl(repository, getter);

        return service;
    }

    @Test
    public void whenExcute_scrapsStaceyHomemakeSite() throws IOException {
        String url = "https://www.staceyhomemaker.com/buffalo-cauliflower-tacos/";
        RecipeServiceImpl service =  mockSite("src/test/resources/recipes/bomb-ass-sample.html");
        Recipe recipe = service.scrapeUrl(url);
        String name = recipe.getName();
        Assert.assertEquals("Bomb Ass Buffalo Cauliflower Tacos",name);
    }

    @Test
    public void whenExcute_scrapsFoodNetworkSite() throws IOException {
        /*
         * Food Network uses a structure of divs and paragraph tags. Will need to walk the tree
         *                         o-Ingredients
         *                      /                 \
         *       o-Ingredients__m-Header       o-Ingredients__m-Body
         *
         *
         * */
        String url = "https://www.foodnetwork.com/recipes/food-network-kitchen/jambalaya-3362212";
        RecipeServiceImpl service =  mockSite("src/test/resources/recipes/jambalaya-recipe.html");
        Recipe recipe = service.scrapeUrl(url);
        String name = recipe.getName();
        Assert.assertEquals("Jambalaya Recipe | Food Network Kitchen | Food Network",name);
    }


}
