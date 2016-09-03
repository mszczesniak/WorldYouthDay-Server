package pl.kielce.tu.worldyouthday.scheduler.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class UpdateEventDetailsResource implements Serializable {

    private final String title;
    private final String description;
    private final Long version;

    @JsonCreator
    public UpdateEventDetailsResource(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("version") Long version) {

        this.title = title;
        this.description = description;
        this.version = version;
    }

    public UpdateEventDetailsResource(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.version = builder.version;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateEventDetailsResource that = (UpdateEventDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdateEventDetailsResource{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;
        private Long version;

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdateEventDetailsResource build() {
            return new UpdateEventDetailsResource(this);
        }

    }


}
