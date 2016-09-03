package pl.kielce.tu.worldyouthday.utils.tag;

import org.jsoup.nodes.Element;

/**
 * Created by Łukasz Wesołowski on 27.04.2016.
 */
public class PrayerTag implements Tag {
    private final String ATTRIBUTE_ID = "id";

    private String id;

    public PrayerTag(Element element) {
        id = element.hasAttr(ATTRIBUTE_ID) ? element.attr(ATTRIBUTE_ID) : null;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrayerTag prayerTag = (PrayerTag) o;

        return id != null ? id.equals(prayerTag.id) : prayerTag.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PrayerTag{" +
                "id='" + id + '\'' +
                '}';
    }
}
