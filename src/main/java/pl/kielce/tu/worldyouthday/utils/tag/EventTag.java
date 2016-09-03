package pl.kielce.tu.worldyouthday.utils.tag;

import org.jsoup.nodes.Element;

/**
 * Created by Łukasz Wesołowski on 02.06.2016.
 */
public class EventTag implements Tag {
    private final String ATTRIBUTE_ID = "id";

    private String id;

    public EventTag(Element element) {
        id = element.hasAttr(ATTRIBUTE_ID) ? element.attr(ATTRIBUTE_ID) : null;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventTag eventTag = (EventTag) o;

        return id != null ? id.equals(eventTag.id) : eventTag.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "EventTag{" +
                "id='" + id + '\'' +
                '}';
    }
}
