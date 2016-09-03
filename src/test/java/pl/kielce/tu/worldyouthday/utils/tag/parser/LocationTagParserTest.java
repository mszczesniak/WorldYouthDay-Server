package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.kielce.tu.worldyouthday.Application;
import pl.kielce.tu.worldyouthday.utils.tag.LocationTag;

import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
public class LocationTagParserTest {
    @Autowired
    private LocationTagParser locationTagParser;

    @Test
    public void shouldFindLocationTag() {
        String text = "Witamy w <location latitude='23.45' longitude='34.22'>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(23.45, results.get(0).getLatitude().doubleValue(), 0.01);
        Assert.assertEquals(34.22, results.get(0).getLongitude().doubleValue(), 0.01);
    }

    @Test
    public void shouldFindLocationTagWithoutLatitude() {
        String text = "Witamy w <location longitude='34.22'>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getLatitude());
        Assert.assertEquals(34.22, results.get(0).getLongitude().doubleValue(), 0.01);
    }

    @Test
    public void shouldFindLocationTagWithoutLongitude() {
        String text = "Witamy w <location latitude='23.45'>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(23.45, results.get(0).getLatitude().doubleValue(), 0.01);
        Assert.assertEquals(null, results.get(0).getLongitude());
    }

    @Test
    public void shouldFindLocationTagWithoutLongitudeAndLatitude() {
        String text = "Witamy w <location>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getLatitude());
        Assert.assertEquals(null, results.get(0).getLongitude());
    }

    @Test
    public void shouldFindLocationTagWithEmptyAttribute() {
        String text = "Witamy w <location latitude='' longitude=''>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(1, results.size());
        Assert.assertEquals(null, results.get(0).getLatitude());
        Assert.assertEquals(null, results.get(0).getLongitude());
    }

    @Test
    public void shouldFindMultipleLocationTag() {
        String text = "Witamy w <location latitude='23.45' longitude='34.22'>domu</location> lub <location latitude='14.15' longitude='11.11'>domu</location> lub <location latitude='12.32' longitude='4.55'>domu</location>";

        List<LocationTag> results = locationTagParser.parse(text);

        Assert.assertEquals(3, results.size());
        Assert.assertEquals(23.45, results.get(0).getLatitude().doubleValue(), 0.01);
        Assert.assertEquals(34.22, results.get(0).getLongitude().doubleValue(), 0.01);
        Assert.assertEquals(14.15, results.get(1).getLatitude().doubleValue(), 0.01);
        Assert.assertEquals(11.11, results.get(1).getLongitude().doubleValue(), 0.01);
        Assert.assertEquals(12.32, results.get(2).getLatitude().doubleValue(), 0.01);
        Assert.assertEquals(4.55, results.get(2).getLongitude().doubleValue(), 0.01);
    }

    @Test(expected = NumberFormatException.class)
    public void shouldFindLocationTagWithoutValidCoordinates() {
        String text = "Witamy w <location latitude='av' longitude='12as'>domu</location>";

        locationTagParser.parse(text);
    }
}
