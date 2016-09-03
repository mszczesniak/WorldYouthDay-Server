package pl.kielce.tu.worldyouthday.prayer.resources;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement
public class PrayerResource implements Serializable {

    private final String id;
    private final Map<Language, PrayerDetailsResource> details;
    private final Long version;

    public PrayerResource(Builder builder) {
        this.id = builder.id;
        this.details = builder.details;
        this.version = builder.version;
    }

    public String getId() {
        return id;
    }

    public Map<Language, PrayerDetailsResource> getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrayerResource that = (PrayerResource) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PrayerResource{" +
                "id='" + id + '\'' +
                ", details=" + details +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private Map<Language, PrayerDetailsResource> details = new HashMap<>();
        private Long version;


        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withDetail(Language language, PrayerDetailsResource details) {
            this.details.put(language, details);
            return this;
        }

        public Builder withDetails(Map<Language, PrayerDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public PrayerResource build() {
            return new PrayerResource(this);
        }

    }


}
