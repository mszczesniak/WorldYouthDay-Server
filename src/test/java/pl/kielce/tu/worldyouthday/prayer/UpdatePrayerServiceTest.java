package pl.kielce.tu.worldyouthday.prayer;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.UpdatePrayerResource;
import pl.kielce.tu.worldyouthday.prayer.services.UpdatePrayerService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class UpdatePrayerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private UpdatePrayerService updatePrayerService;

    @Test
    public void shouldUpdatePrayer() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.GERMAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        PrayerResource result = updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);


        Assert.assertEquals(2, result.getDetails().size());
        Assert.assertEquals("TestTytul1", result.getDetails().get(Language.GERMAN).getTitle());
        Assert.assertEquals("TestTresc1", result.getDetails().get(Language.GERMAN).getContent());
        Assert.assertEquals("TestTitle1", result.getDetails().get(Language.ENGLISH).getTitle());
        Assert.assertEquals("TestContent1", result.getDetails().get(Language.ENGLISH).getContent());
        Assert.assertFalse(result.getDetails().containsKey(Language.POLISH));
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void shouldThrowObjectOptimisticLockingFailureExceptionBecauseVersionInPrayerResourceIsNotIncremented() throws Exception {

        UpdatePrayerResource resource1 = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.GERMAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        UpdatePrayerResource resource2 = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle2")
                                .withContent("TestContent2")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.ITALIAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul2")
                                .withContent("TestTresc2")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource1);
        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource2);
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void shouldThrowObjectOptimisticLockingFailureExceptionBecauseVersionInPrayerDetailsResourceIsNotIncremented() throws Exception {

        UpdatePrayerResource resource1 = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.GERMAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        UpdatePrayerResource resource2 = UpdatePrayerResource
                .newBuilder()
                .withVersion(1L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle2")
                                .withContent("TestContent2")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.ITALIAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul2")
                                .withContent("TestTresc2")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource1);
        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource2);
    }


    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseNotContainsDetailsInDefaultLanguage() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.POLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .withDetails(Language.GERMAN, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTytul1")
                                .withContent("TestTresc1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseDetailsIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(null)
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseIdNotExist() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.POLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("niema", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseIdIsEmpty() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.POLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseIdIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.POLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer(null, resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseResourceIsNull() throws Exception {

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", null);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseLanguageIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(null, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseVersionIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(null)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseVersionInDetailsIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("TestContent1")
                                .withVersion(null)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseVersionInContentIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle(null)
                                .withContent("TestContent")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseContentIsNull() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle")
                                .withContent(null)
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseTitleIsEmpty() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("")
                                .withContent("TestContent1")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenUpdatePrayerBecauseContentIsEmpty() throws Exception {

        UpdatePrayerResource resource = UpdatePrayerResource
                .newBuilder()
                .withVersion(0L)
                .withDetails(Language.ENGLISH, UpdatePrayerDetailsResource
                                .newBuilder()
                                .withTitle("TestTitle1")
                                .withContent("")
                                .withVersion(0L)
                                .build()
                )
                .build();

        updatePrayerService.updatePrayer("238a44d8-0953-11e6-b512-3e1d05defe78", resource);
    }

    //TODO mateusz testy minimalna liczbe znakow w tlumaczeniu i nazwie


}
