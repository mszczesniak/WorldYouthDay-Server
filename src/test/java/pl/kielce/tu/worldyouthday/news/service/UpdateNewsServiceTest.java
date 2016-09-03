package pl.kielce.tu.worldyouthday.news.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.news.resource.NewsDetailsResource;
import pl.kielce.tu.worldyouthday.news.resource.NewsResource;
import pl.kielce.tu.worldyouthday.news.resource.UpdateNewsResource;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

/**
 * Created by Łukasz Wesołowski on 24.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UpdateNewsServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
    @Autowired
    private UpdateNewsService updateNewsService;

    @Autowired
    private FindNewsService findNewsService;

    @Test
    public void shouldUpdateNews() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event>")
                        .build()
                )
                .withVersion(1L)
                .build();

        NewsResource newsResource = updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
        String id = newsResource.getId();

        NewsResource result = findNewsService.findNews(id);

        Assert.assertEquals(3, result.getDetails().size());
        Assert.assertEquals("Zmieniono tekst <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> " +
                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>", result.getDetails().get(Language.POLISH).getText());
        Assert.assertEquals("Zmieniono tytuł", result.getDetails().get(Language.POLISH).getTitle());
        Assert.assertEquals("Zmieniono tekst EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                "<event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>", result.getDetails().get(Language.ENGLISH).getText());
        Assert.assertEquals("Zmieniono tytuł EN", result.getDetails().get(Language.ENGLISH).getTitle());
        Assert.assertEquals("Zmieniono tekst FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <event id='1d996c1c-0d76-11e6-a148-3e1d05defe78'>wydarzenie</event>", result.getDetails().get(Language.FRENCH).getText());
        Assert.assertEquals("Zmieniono tytuł FR", result.getDetails().get(Language.FRENCH).getTitle());
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutValidTitle() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutValidText() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithLocationTagWithoutValidCoordinates() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='asd' longitude='1asd'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithLocationTagWithoutLatitude() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithLocationTagWithoutLongitude() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45''>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithLocationTagWithoutAttributes() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithPointOfInterestTagWithoutValidId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithPointOfInterestTagWithoutId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithEventTagWithoutValidId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa")
                        .withText("Text nowego newsa <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<event id='BLEEE'>wydarzenie</event> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa EN")
                        .withText("Text nowego newsa EN <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithEventTagWithoutId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='238a44d8-0953-11e6-b512-3e1d05defe78'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithPrayerTagWithoutValidId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
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
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Tytuł nowego newsa FR")
                        .withText("Text nowego newsa FR <location latitude='23.45' longitude='45.11'>lokacja</location> " +
                                "<poi id='b9288672-14d9-42cc-a855-f4f9f4d75ce2'>poi</poi> <prayer id='BLEE'>modlitwa</prayer>")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews(null, resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutValidId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("BLEE", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutAuthorId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutValidAuthorId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("BLEEE")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutCityId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutValidCityId() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("BLEEE")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutDetails() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutDetailsInDefaultLanguage() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(1L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldNotUpdateNewsWithoutVersion() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }

    @Test(expected = OptimisticLockingFailureException.class)
    public void shouldNotUpdateNewsWithoutValidVersion() throws ValidationException {
        UpdateNewsResource resource = UpdateNewsResource.newBuilder()
                .withCityId("4154d988-03bd-11e6-b512-3e1d05defe78")
                .withAuthorId("9ac1bf56-e499-11e5-9730-9a79f06e9478")
                .withDetails(Language.POLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł")
                        .withText("Zmieniono tekst")
                        .build()
                )
                .withDetails(Language.ENGLISH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł EN")
                        .withText("Zmieniono tekst EN")
                        .build()
                )
                .withDetails(Language.FRENCH, NewsDetailsResource.newBuilder()
                        .withTitle("Zmieniono tytuł FR")
                        .withText("Zmieniono tekst FR")
                        .build()
                )
                .withVersion(25L)
                .build();

        updateNewsService.updateNews("530994c3-6ca0-4402-af96-48f134473b8f", resource);
    }
}
