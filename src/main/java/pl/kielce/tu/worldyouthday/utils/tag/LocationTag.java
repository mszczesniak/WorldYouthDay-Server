package pl.kielce.tu.worldyouthday.utils.tag;

import com.google.common.base.Strings;
import org.jsoup.nodes.Element;

/**
 * Created by Łukasz Wesołowski on 27.04.2016.
 */
public class LocationTag implements Tag {
    private final String ATTRIBUTE_LATITUDE = "latitude";
    private final String ATTRIBUTE_LONGITUDE = "longitude";

    private Double latitude;
    private Double longitude;

    public LocationTag(Element element) {
        latitude = Strings.isNullOrEmpty(element.attr(ATTRIBUTE_LATITUDE)) ? null : Double.valueOf(element.attr(ATTRIBUTE_LATITUDE));
        longitude = Strings.isNullOrEmpty(element.attr(ATTRIBUTE_LONGITUDE)) ? null : Double.valueOf(element.attr(ATTRIBUTE_LONGITUDE));
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocationTag that = (LocationTag) o;

        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        return longitude != null ? longitude.equals(that.longitude) : that.longitude == null;

    }

    @Override
    public int hashCode() {
        int result = latitude != null ? latitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocationTag{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
