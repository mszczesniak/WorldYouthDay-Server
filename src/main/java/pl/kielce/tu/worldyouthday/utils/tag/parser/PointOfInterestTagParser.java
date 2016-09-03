package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.tag.PointOfInterestTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Wesołowski on 04.05.2016.
 */

@Component
public class PointOfInterestTagParser implements TagParser<PointOfInterestTag> {
    private final String TAG_NAME = "poi";

    @Override
    public List<PointOfInterestTag> parse(String text) {
        List<PointOfInterestTag> pointOfInterestTags = new ArrayList<>();

        Document document = Jsoup.parse(text);

        Elements elements = document.select(TAG_NAME);
        elements.stream().forEach(e -> pointOfInterestTags.add(new PointOfInterestTag(e)));

        return pointOfInterestTags;
    }
}
