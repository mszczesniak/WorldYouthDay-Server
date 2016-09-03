package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.tag.PrayerTag;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class PrayerTagParserTest {
    @Autowired
    private PrayerTagParser prayerTagParser;

    @Test
    public void shouldFindPrayerTag() {
        String text = "Oto jest <prayer id='1'>modlitwa</poi>";

        List<PrayerTag> results = prayerTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("1", results.get(0).getId());
    }

    @Test
    public void shouldFindPrayerTagWithoutId() {
        String text = "Oto jest <prayer>modlitwa</poi>";

        List<PrayerTag> results = prayerTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getId());
    }

    @Test
    public void shouldFindPrayerTagWithEmptyAttribute() {
        String text = "Oto jest <prayer id=''>modlitwa</poi>";

        List<PrayerTag> results = prayerTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("", results.get(0).getId());
    }

    @Test
    public void shouldFindMultiplePrayerTag() {
        String text = "Oto jest <prayer id='1'>modlitwa</poi> i ta <prayer id='asd23'>druga</poi>";

        List<PrayerTag> results = prayerTagParser.parse(text);

        Assert.assertEquals(2, results.size());
        Assert.assertEquals("1", results.get(0).getId());
        Assert.assertEquals("asd23", results.get(1).getId());
    }
}
