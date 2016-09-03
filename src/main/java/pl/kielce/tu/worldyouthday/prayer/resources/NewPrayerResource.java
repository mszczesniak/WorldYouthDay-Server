package pl.kielce.tu.worldyouthday.prayer.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;

@XmlRootElement
public class NewPrayerResource implements Serializable {

    private final HashMap<Language, NewPrayerDetailsResource> details;

    @JsonCreator
    public NewPrayerResource(@JsonProperty("details") HashMap<Language, NewPrayerDetailsResource> details) {
        this.details = details;
    }

    public NewPrayerResource(Builder builder) {
        this.details = builder.details;
    }

    public HashMap<Language, NewPrayerDetailsResource> getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPrayerResource that = (NewPrayerResource) o;

        return !(details != null ? !details.equals(that.details) : that.details != null);

    }

    @Override
    public int hashCode() {
        return details != null ? details.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "NewPrayerResource{" +
                "details=" + details +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private HashMap<Language, NewPrayerDetailsResource> details = new HashMap<>();

        public Builder withDetails(HashMap<Language, NewPrayerDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withDetails(Language language, NewPrayerDetailsResource detailsResource) {
            details.put(language, detailsResource);
            return this;
        }

        public NewPrayerResource build() {
            return new NewPrayerResource(this);
        }

    }


}
