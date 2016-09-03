package pl.kielce.tu.worldyouthday.scheduler.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class EventDetailsResource implements Serializable {

    private final String title;
    private final String description;
    private final Long version;

    public EventDetailsResource(Builder builder) {
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

        EventDetailsResource that = (EventDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return version != null ? version.equals(that.version) : that.version == null;

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
        return "EventDetailsResource{" +
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

        public EventDetailsResource build() {
            return new EventDetailsResource(this);
        }

    }


}
