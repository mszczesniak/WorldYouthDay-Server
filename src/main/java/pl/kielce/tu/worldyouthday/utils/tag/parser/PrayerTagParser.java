package pl.kielce.tu.worldyouthday.utils.tag.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pl.kielce.tu.worldyouthday.utils.tag.PrayerTag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Wesołowski on 27.04.2016.
 */

@Component
public class PrayerTagParser implements TagParser<PrayerTag> {
    private final String TAG_NAME = "prayer";

    @Override
    public List<PrayerTag> parse(String text) {
        List<PrayerTag> prayerTags = new ArrayList<>();

        Document document = Jsoup.parse(text);

        Elements elements = document.select(TAG_NAME);
        elements.stream().forEach(e -> prayerTags.add(new PrayerTag(e)));

        return prayerTags;
    }
}
