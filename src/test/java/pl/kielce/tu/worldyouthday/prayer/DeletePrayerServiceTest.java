package pl.kielce.tu.worldyouthday.prayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.prayer.services.DeletePrayerService;
import pl.kielce.tu.worldyouthday.utils.exception.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class DeletePrayerServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DeletePrayerService deletePrayerService;

    @Test
    public void shouldRemovePrayer() throws Exception {
        String prayerId = "9e4972d4-0953-11e6-b512-3e1d05defe78";
        deletePrayerService.removePrayer(prayerId);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemovePrayerBecauseNewsRefersToThePrayer() throws Exception {
        String prayerId = "238a44d8-0953-11e6-b512-3e1d05defe78";
        deletePrayerService.removePrayer(prayerId);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemovePrayerBecauseIdNotExist() throws Exception {
        String prayerId = "niema";
        deletePrayerService.removePrayer(prayerId);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemovePrayerBecauseIdIsEmpty() throws Exception {
        String prayerId = "";
        deletePrayerService.removePrayer(prayerId);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenRemovePrayerBecauseIdIsNull() throws Exception {
        String prayerId = null;
        deletePrayerService.removePrayer(prayerId);
    }

}
