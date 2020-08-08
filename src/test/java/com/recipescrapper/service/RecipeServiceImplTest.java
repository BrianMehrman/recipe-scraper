package com.recipescrapper.service;

import org.cactoos.text.TextOf;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.io.*;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@RunWith(PowerMockRunner.class)
@PrepareForTest({Jsoup.class})
public class RecipeServiceImplTest {

    @Autowired
    private RecipeService service;

    @Autowired
    private EntityManager em;

    private String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    @Test
    public void whenExcute_scrapsHtml() throws IOException {
        String url = "https://www.staceyhomemaker.com/buffalo-cauliflower-tacos/";
        String htmlString = new TextOf(
                new File("src/test/resources/recipes/bomb-ass-sample.html")
        ).asString();

        Document html = Jsoup.parse(htmlString);

        PowerMockito.mockStatic(Jsoup.class);
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(connection.execute()).thenThrow(new IOException("test"));
        Mockito.when(connection.get()).thenReturn(html);
        PowerMockito.when(Jsoup.connect(Mockito.anyString())).
                thenReturn(connection);


        Document doc = Jsoup.connect(url).get();
        Element title = doc.select("title").first();

        String name = title.text();

        Assert.assertEquals("Bomb Ass Buffalo Cauliflower Tacos",name);
    }
}
