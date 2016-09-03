package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.tag.EventTag;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Łukasz Wesołowski on 02.06.2016.
 */

@Component
public class EventTagParser implements TagParser<EventTag> {
    private final String TAG_NAME = "event";

    @Override
    public List<EventTag> parse(String text) {
        Document document = Jsoup.parse(text);

        Elements elements = document.select(TAG_NAME);
        List<EventTag> locationTags = elements.stream().map(e -> new EventTag(e)).collect(Collectors.toList());

        return locationTags;
    }
}
