package pl.kielce.tu.worldyouthday.scheduler.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class NewEventDetailsResource implements Serializable {

    private final String title;
    private final String description;

    @JsonCreator
    public NewEventDetailsResource(
            @JsonProperty("title") String title,
            @JsonProperty("content") String description) {

        this.title = title;
        this.description = description;
    }

    public NewEventDetailsResource(Builder builder) {
        this.title = builder.title;
        this.description = builder.description;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewEventDetailsResource that = (NewEventDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return !(description != null ? !description.equals(that.description) : that.description != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPrayerDetailsResource{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String description;


        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public NewEventDetailsResource build() {
            return new NewEventDetailsResource(this);
        }

    }


}
