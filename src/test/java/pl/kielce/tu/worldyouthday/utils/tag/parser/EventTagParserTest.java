package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.tag.EventTag;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 02.06.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class EventTagParserTest {
    @Autowired
    private EventTagParser eventTagParser;

    @Test
    public void shouldFindPointOfInterestTag() {
        String text = "<event id='1'>Spotkanie</event> zostało przełożone";

        List<EventTag> results = eventTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("1", results.get(0).getId());
    }

    @Test
    public void shouldFindPointOfInterestTagWithoutId() {
        String text = "<event>Spotkanie</event> zostało przełożone";

        List<EventTag> results = eventTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getId());
    }

    @Test
    public void shouldFindPointOfInterestTagWithEmptyAttribute() {
        String text = "<event id=''>Spotkanie</event> zostało przełożone";

        List<EventTag> results = eventTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("", results.get(0).getId());
    }

    @Test
    public void shouldFindMultiplePointOfInterestTag() {
        String text = "<event id='1'>Spotkanie z kukłami</event> oraz <event id='asd23'>spotkanie z wąsami</event> zostało przełożone ";

        List<EventTag> results = eventTagParser.parse(text);

        Assert.assertEquals(2, results.size());
        Assert.assertEquals("1", results.get(0).getId());
        Assert.assertEquals("asd23", results.get(1).getId());
    }
}
