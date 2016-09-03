package pl.kielce.tu.worldyouthday.news.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.NewsSearchCriteria;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FindNewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private FindNewsService findNewsService;

    @Test
    public void shouldFindNewsByCity() {
        NewsSearchCriteria criteria = NewsSearchCriteria.newBuilder()
                .withLanguage(Language.POLISH)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        List<NewsResource> results = findNewsService.findAllNews(criteria);

        List<String> resultTexts = new ArrayList<>();
        List<String> resultTitles = new ArrayList<>();
        for (NewsResource r : results) {
            for (Map.Entry<Language, NewsDetailsResource> e : r.getDetails().entrySet()) {
                resultTexts.add(e.getValue().getText());
                resultTitles.add(e.getValue().getTitle());
            }
        }

        Assert.assertEquals(3, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(resultTexts.contains("Nastapi zmiana organizacji"));
        Assert.assertTrue(resultTexts.contains("Bedzie zarlo ze hej"));
        Assert.assertTrue(resultTitles.contains("Zmiana organizacji"));
        Assert.assertTrue(resultTitles.contains("Zarlo bedzie"));
    }

    @Test
    public void shouldFindNewsByCityInDefaultLanguage() {
        NewsSearchCriteria criteria = NewsSearchCriteria.newBuilder()
                .withLanguage(Language.FRENCH)
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        List<NewsResource> results = findNewsService.findAllNews(criteria);

        List<String> resultTexts = new ArrayList<>();
        List<String> resultTitles = new ArrayList<>();
        for (NewsResource r : results) {
            for (Map.Entry<Language, NewsDetailsResource> e : r.getDetails().entrySet()) {
                resultTexts.add(e.getValue().getText());
                resultTitles.add(e.getValue().getTitle());
            }
        }

        Assert.assertEquals(3, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.ENGLISH));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.ENGLISH));
        Assert.assertTrue(resultTexts.contains("Nastapi zmiana organizacji EN"));
        Assert.assertTrue(resultTexts.contains("Bedzie zarlo ze hej EN"));
        Assert.assertTrue(resultTitles.contains("Zmiana organizacji EN"));
        Assert.assertTrue(resultTitles.contains("Zarlo bedzie EN"));
    }

    @Test
    public void shouldFindAllNewses() {
        NewsSearchCriteria criteria = NewsSearchCriteria.newBuilder()
                .withLanguage(Language.POLISH)
                .build();

        List<NewsResource> results = findNewsService.findAllNews(criteria);

        List<String> resultTexts = new ArrayList<>();
        List<String> resultTitles = new ArrayList<>();
        for (NewsResource r : results) {
            for (Map.Entry<Language, NewsDetailsResource> e : r.getDetails().entrySet()) {
                resultTexts.add(e.getValue().getText());
                resultTitles.add(e.getValue().getTitle());
            }
        }

        Assert.assertEquals(4, results.size());
        Assert.assertTrue(results.get(0).getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(results.get(1).getDetails().containsKey(Language.POLISH));
        Assert.assertTrue(resultTexts.contains("Nastapi zmiana organizacji"));
        Assert.assertTrue(resultTexts.contains("Bedzie zarlo ze hej"));
        Assert.assertTrue(resultTexts.contains("Odwolanie spotkania hehe"));
        Assert.assertTrue(resultTitles.contains("Zmiana organizacji"));
        Assert.assertTrue(resultTitles.contains("Zarlo bedzie"));
        Assert.assertTrue(resultTitles.contains("Odwolanie spotkania"));
    }

    @Test
    public void shouldFindNews() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        NewsResource result = findNewsService.findNews("530994c3-6ca0-4402-af96-48f134473b8f");

        Assert.assertEquals("530994c3-6ca0-4402-af96-48f134473b8f", result.getId());
        Assert.assertEquals("4154d988-03bd-11e6-b512-3e1d05defe78", result.getCityId());
        Assert.assertEquals("9ac1bf56-e499-11e5-9730-9a79f06e9478", result.getCreationAuthorId());
        Assert.assertEquals(dateFormat.parse("2016-03-22 22:12:00"), result.getCreationDate());
        Assert.assertEquals(null, result.getModificationAuthorId());
        Assert.assertEquals(null, result.getModificationDate());
        Assert.assertEquals("Nastapi zmiana organizacji", result.getDetails().get(Language.POLISH).getText());
        Assert.assertEquals("Zmiana organizacji", result.getDetails().get(Language.POLISH).getTitle());
        Assert.assertEquals(1L, result.getVersion().longValue());
        Assert.assertTrue(result.getDetails().containsKey(Language.POLISH));
    }

    @Test
    public void shouldFindNewsInDefaultLanguage() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        NewsResource result = findNewsService.findNews("530994c3-6ca0-4402-af96-48f134473b8f");

        Assert.assertEquals("530994c3-6ca0-4402-af96-48f134473b8f", result.getId());
        Assert.assertEquals("4154d988-03bd-11e6-b512-3e1d05defe78", result.getCityId());
        Assert.assertEquals("9ac1bf56-e499-11e5-9730-9a79f06e9478", result.getCreationAuthorId());
        Assert.assertEquals(dateFormat.parse("2016-03-22 22:12:00"), result.getCreationDate());
        Assert.assertEquals(null, result.getModificationAuthorId());
        Assert.assertEquals(null, result.getModificationDate());
        Assert.assertEquals("Nastapi zmiana organizacji EN", result.getDetails().get(Language.ENGLISH).getText());
        Assert.assertEquals("Zmiana organizacji EN", result.getDetails().get(Language.ENGLISH).getTitle());
        Assert.assertEquals(1L, result.getVersion().longValue());
        Assert.assertTrue(result.getDetails().containsKey(Language.ENGLISH));
    }

    @Test
    public void shouldFindNewsInAllLanguages() {
        NewsResource result = findNewsService.findNews("530994c3-6ca0-4402-af96-48f134473b8f");

        Assert.assertEquals(2, result.getDetails().size());
        Assert.assertEquals("Nastapi zmiana organizacji", result.getDetails().get(Language.POLISH).getText());
        Assert.assertEquals("Zmiana organizacji", result.getDetails().get(Language.POLISH).getTitle());
        Assert.assertEquals("Nastapi zmiana organizacji EN", result.getDetails().get(Language.ENGLISH).getText());
        Assert.assertEquals("Zmiana organizacji EN", result.getDetails().get(Language.ENGLISH).getTitle());
    }
}
