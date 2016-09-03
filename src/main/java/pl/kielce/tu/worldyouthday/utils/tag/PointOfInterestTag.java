package pl.kielce.tu.worldyouthday.utils.tag;

import org.jsoup.nodes.Element;

/**
 * Created by Łukasz Wesołowski on 27.04.2016.
 */
public class PointOfInterestTag implements Tag {
    private final String ATTRIBUTE_ID = "id";

    private String id;

    public PointOfInterestTag(Element element) {
        id = element.hasAttr(ATTRIBUTE_ID) ? element.attr(ATTRIBUTE_ID) : null;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointOfInterestTag that = (PointOfInterestTag) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PointOfInterestTag{" +
                "id='" + id + '\'' +
                '}';
    }
}
