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
import pl.kielce.tu.worldyouthday.news.resource.NewNewsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AddNewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private AddNewsService addNewsService;

    @Autowired
    private FindNewsService findNewsService;

    @Test
    public void shouldAddNews() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        String id = addNewsService.addNews(resource).getId();

        NewsResource result = findNewsService.findNews(id);

        Assert.assertEquals(3, result.getDetails().size());
        Assert.assertEquals("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>", result.getDetails().get(Language.POLISH).getText());
        Assert.assertEquals("Tytuł nowego newsa", result.getDetails().get(Language.POLISH).getTitle());
        Assert.assertEquals("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event>", result.getDetails().get(Language.ENGLISH).getText());
        Assert.assertEquals("Tytuł nowego newsa EN", result.getDetails().get(Language.ENGLISH).getTitle());
        Assert.assertEquals("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                "<event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>", result.getDetails().get(Language.GERMAN).getText());
        Assert.assertEquals("Tytuł nowego newsa DE", result.getDetails().get(Language.GERMAN).getTitle());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutValidTitle() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithLocationTagWithoutValidCoordinates() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='asd' longitude='112aa'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithLocationTagWithoutLatitude() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithLocationTagWithoutLongitude() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithLocationTagWithoutAttributes() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithPointOfInterestTagWithoutValidId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='BLEE'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithPointOfInterestTagWithoutId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithEventTagWithoutValidId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<event id='BLEE'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithEventTagWithoutId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<event>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithPrayerTagWithoutValidId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='BLEEE'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithPrayerTagWithoutId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutValidText() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutAuthorId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutValidAuthorId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("BLEEE")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutCityId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutValidCityId() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("BLEEE")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutDetails() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .build();

        addNewsService.addNews(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotAddNewsWithoutDetailsInDefaultLanguage() throws ValidationException {
        NewNewsResource resource = NewNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa")
                        .build()
                )
                .withDetails(Language.GERMAN, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa DE")
                        .withText("Text nowego newsa DE")
                        .build()
                )
                .build();

        addNewsService.addNews(resource);
    }
}
