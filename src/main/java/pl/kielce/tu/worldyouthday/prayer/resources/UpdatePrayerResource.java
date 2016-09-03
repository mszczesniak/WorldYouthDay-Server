package pl.kielce.tu.worldyouthday.prayer.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.kielce.tu.worldyouthday.language.Language;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashMap;

@XmlRootElement
public class UpdatePrayerResource implements Serializable {

    private final HashMap<Language, UpdatePrayerDetailsResource> details;
    private final Long version;

    @JsonCreator
    public UpdatePrayerResource(
            @JsonProperty("details") HashMap<Language, UpdatePrayerDetailsResource> details,
            @JsonProperty("version") Long version) {
        this.details = details;
        this.version = version;
    }

    public UpdatePrayerResource(Builder builder) {
        this.details = builder.details;
        this.version = builder.version;
    }

    public HashMap<Language, UpdatePrayerDetailsResource> getDetails() {
        return details;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdatePrayerResource that = (UpdatePrayerResource) o;

        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = details != null ? details.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdatePrayerResource{" +
                "details=" + details +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private HashMap<Language, UpdatePrayerDetailsResource> details = new HashMap<>();
        private Long version;

        public Builder withDetails(HashMap<Language, UpdatePrayerDetailsResource> details) {
            this.details = details;
            return this;
        }

        public Builder withDetails(Language language, UpdatePrayerDetailsResource updatePrayerDetailsResource) {
            this.details.put(language, updatePrayerDetailsResource);
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdatePrayerResource build() {
            return new UpdatePrayerResource(this);
        }

    }


}
