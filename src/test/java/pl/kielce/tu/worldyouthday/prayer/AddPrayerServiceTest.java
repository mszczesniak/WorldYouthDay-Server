package pl.kielce.tu.worldyouthday.prayer;

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
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.NewPrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.services.AddPrayerService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class AddPrayerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private AddPrayerService addPrayerService;

    @Test
    public void shouldAddPrayer() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.ENGLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .build()
                )
                .withDetails(Language.POLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .build()
                )
                .build();

        PrayerResource result = addPrayerService.addPrayer(resource);


        Assert.assertEquals(2, result.getDetails().size());
        Assert.assertEquals("TestTytul1", result.getDetails().get(Language.POLISH).getTitle());
        Assert.assertEquals("TestTresc1", result.getDetails().get(Language.POLISH).getContent());
        Assert.assertEquals("TestTitle1", result.getDetails().get(Language.ENGLISH).getTitle());
        Assert.assertEquals("TestContent1", result.getDetails().get(Language.ENGLISH).getContent());
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerDetailsDefaultLanguageIsMandatory() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.GERMAN, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .build()
                )
                .withDetails(Language.POLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .build()
                )
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseDetailsIsEmpty() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseDetailsIsNull() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(null)
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseTitleIsEmpty() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.ENGLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("")
                                .withContent("TestContent1")
                                .build()
                )
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseTitleIsNull() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.ENGLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle(null)
                                .withContent("TestContent1")
                                .build()
                )
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseContentIsEmpty() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.ENGLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("")
                                .build()
                )
                .build();

        addPrayerService.addPrayer(resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenAddPrayerBecauseContentIsNull() throws Exception {

        NewPrayerResource resource = NewPrayerResource
                .newBuilder()
                .withDetails(Language.ENGLISH, NewPrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent(null)
                                .build()
                )
                .build();

        addPrayerService.addPrayer(resource);
    }

    //TODO mateusz testy minimalna liczbe znakow w tlumaczeniu i nazwie


}
