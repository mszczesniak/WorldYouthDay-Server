package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.tag.PointOfInterestTag;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class PointOfInterestTagParserTest {
    @Autowired
    private PointOfInterestTagParser pointOfInterestTagParser;

    @Test
    public void shouldFindPointOfInterestTag() {
        String text = "Witamy w <poi id='1'>zabytku</poi>";

        List<PointOfInterestTag> results = pointOfInterestTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("1", results.get(0).getId());
    }

    @Test
    public void shouldFindPointOfInterestTagWithoutId() {
        String text = "Witamy w <poi>zabytku</poi>";

        List<PointOfInterestTag> results = pointOfInterestTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getId());
    }

    @Test
    public void shouldFindPointOfInterestTagWithEmptyAttribute() {
        String text = "Witamy w <poi id=''>zabytku</poi>";

        List<PointOfInterestTag> results = pointOfInterestTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals("", results.get(0).getId());
    }

    @Test
    public void shouldFindMultiplePointOfInterestTag() {
        String text = "Witamy w <poi id='1'>zabytku</poi> lub <poi id='asd23'>budynku</poi> ";

        List<PointOfInterestTag> results = pointOfInterestTagParser.parse(text);

        Assert.assertEquals(2, results.size());
        Assert.assertEquals("1", results.get(0).getId());
        Assert.assertEquals("asd23", results.get(1).getId());
    }
}
