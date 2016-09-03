package pl.kielce.tu.worldyouthday.prayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.language.Language;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerDetailsResource;
import pl.kielce.tu.worldyouthday.prayer.resources.PrayerResource;
import pl.kielce.tu.worldyouthday.prayer.services.FindPrayerService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class FindPrayerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private FindPrayerService findPrayerService;

    @Test
    public void shouldFindAllPrayers() throws Exception {

        PrayerSearchCriteria searchCriteria = PrayerSearchCriteria.
                newBuilder().
                build();

        List<PrayerResource> results = findPrayerService.getPrayers(searchCriteria);
        assertEquals(results.size(), 2);

        PrayerResource resource1 = results.get(0);
        assertEquals(resource1.getId(), "238a44d8-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource1.getVersion(), new Long(0L));
        PrayerDetailsResource detailsPolishResource1 = resource1.getDetails().get(Language.POLISH);
        assertNotNull(detailsPolishResource1);
        assertEquals(detailsPolishResource1.getTitle(), "Tytul1");
        assertEquals(detailsPolishResource1.getContent(), "Tresc1");
        assertEquals(detailsPolishResource1.getVersion(), new Long(0L));
        PrayerDetailsResource detailsEnglishResource1 = resource1.getDetails().get(Language.ENGLISH);
        assertNotNull(detailsEnglishResource1);
        assertEquals(detailsEnglishResource1.getTitle(), "Title1");
        assertEquals(detailsEnglishResource1.getContent(), "Content1");
        assertEquals(detailsEnglishResource1.getVersion(), new Long(0L));

        PrayerResource resource2 = results.get(1);
        assertEquals(resource2.getId(), "9e4972d4-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource2.getVersion(), new Long(0L));
        PrayerDetailsResource detailsPolishResource2 = resource2.getDetails().get(Language.POLISH);
        assertNotNull(detailsPolishResource2);
        assertEquals(detailsPolishResource2.getTitle(), "Tytul2");
        assertEquals(detailsPolishResource2.getContent(), "Tresc2");
        assertEquals(detailsPolishResource2.getVersion(), new Long(0L));
        PrayerDetailsResource detailsEnglishResource2 = resource2.getDetails().get(Language.ENGLISH);
        assertNotNull(detailsEnglishResource2);
        assertEquals(detailsEnglishResource2.getTitle(), "Title2");
        assertEquals(detailsEnglishResource2.getContent(), "Content2");
        assertEquals(detailsEnglishResource2.getVersion(), new Long(0L));
    }

    @Test
    public void shouldFindAllPrayersInEnglish() throws Exception {

        PrayerSearchCriteria searchCriteria = PrayerSearchCriteria
                .newBuilder()
                .withLanguage(Language.ENGLISH)
                .build();

        List<PrayerResource> results = findPrayerService.getPrayers(searchCriteria);
        assertEquals(results.size(), 2);

        PrayerResource resource1 = results.get(0);
        assertEquals(resource1.getId(), "238a44d8-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource1.getVersion(), new Long(0L));
        PrayerDetailsResource detailsPolishResource1 = resource1.getDetails().get(Language.POLISH);
        assertNull(detailsPolishResource1);
        PrayerDetailsResource detailsEnglishResource1 = resource1.getDetails().get(Language.ENGLISH);
        assertNotNull(detailsEnglishResource1);
        assertEquals(detailsEnglishResource1.getTitle(), "Title1");
        assertEquals(detailsEnglishResource1.getContent(), "Content1");
        assertEquals(detailsEnglishResource1.getVersion(), new Long(0L));

        PrayerResource resource2 = results.get(1);
        assertEquals(resource2.getId(), "9e4972d4-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource2.getVersion(), new Long(0L));
        PrayerDetailsResource detailsPolishResource2 = resource2.getDetails().get(Language.POLISH);
        assertNull(detailsPolishResource2);
        PrayerDetailsResource detailsEnglishResource2 = resource2.getDetails().get(Language.ENGLISH);
        assertNotNull(detailsEnglishResource2);
        assertEquals(detailsEnglishResource2.getTitle(), "Title2");
        assertEquals(detailsEnglishResource2.getContent(), "Content2");
        assertEquals(detailsEnglishResource2.getVersion(), new Long(0L));
    }

    @Test
    public void shouldFindDefaultPrayersForLanguage() throws Exception {

        List<PrayerResource> result1 = findPrayerService.getPrayers(PrayerSearchCriteria
                .newBuilder()
                .withLanguage(Language.POLISH)
                .build());
        List<PrayerResource> result2 = findPrayerService.getPrayers(PrayerSearchCriteria
                .newBuilder()
                .withLanguage(Language.ENGLISH)
                .build());
        List<PrayerResource> result3 = findPrayerService.getPrayers(PrayerSearchCriteria
                .newBuilder()
                .withLanguage(Language.FRENCH)
                .build());

        assertEquals(result1.size(), 2);
        assertEquals(result2.size(), 2);
        assertEquals(result3.size(), 2);
        assertNotNull(result3.get(0).getDetails().get(Language.getDefault()));
        assertNotNull(result3.get(1).getDetails().get(Language.getDefault()));

    }

    @Test
    public void shouldFindPrayerForId() throws Exception {

        PrayerResource resource = findPrayerService.getPrayerForId("9e4972d4-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource.getId(), "9e4972d4-0953-11e6-b512-3e1d05defe78");
        assertEquals(resource.getVersion(), new Long(0L));
        PrayerDetailsResource detailsPolishResource2 = resource.getDetails().get(Language.POLISH);
        assertNotNull(detailsPolishResource2);
        assertEquals(detailsPolishResource2.getTitle(), "Tytul2");
        assertEquals(detailsPolishResource2.getContent(), "Tresc2");
        assertEquals(detailsPolishResource2.getVersion(), new Long(0L));
        PrayerDetailsResource detailsEnglishResource2 = resource.getDetails().get(Language.ENGLISH);
        assertNotNull(detailsEnglishResource2);
        assertEquals(detailsEnglishResource2.getTitle(), "Title2");
        assertEquals(detailsEnglishResource2.getContent(), "Content2");
        assertEquals(detailsEnglishResource2.getVersion(), new Long(0L));

    }


    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionBecauseIdNotExist() throws Exception {
        findPrayerService.getPrayerForId("niema");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionBecauseIdIsEmpty() throws Exception {
        findPrayerService.getPrayerForId("");
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionBecauseIdIsNull() throws Exception {
        findPrayerService.getPrayerForId(null);
    }
}
