package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.tag.LocationTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@Component
public class LocationTagParser implements TagParser<LocationTag> {
    private final String TAG_NAME = "location";

    @Override
    public List<LocationTag> parse(String text) {
        List<LocationTag> locationTags = new ArrayList<>();

        Document document = Jsoup.parse(text);

        Elements elements = document.select(TAG_NAME);
        elements.stream().forEach(e -> locationTags.add(new LocationTag(e)));

        return locationTags;
    }
}
