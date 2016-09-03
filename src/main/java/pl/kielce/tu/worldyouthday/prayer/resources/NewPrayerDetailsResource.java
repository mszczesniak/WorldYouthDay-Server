package pl.kielce.tu.worldyouthday.prayer.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class NewPrayerDetailsResource implements Serializable {

    private final String title;
    private final String content;

    @JsonCreator
    public NewPrayerDetailsResource(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content) {

        this.title = title;
        this.content = content;
    }

    public NewPrayerDetailsResource(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPrayerDetailsResource that = (NewPrayerDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return !(content != null ? !content.equals(that.content) : that.content != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPrayerDetailsResource{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String content;


        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public NewPrayerDetailsResource build() {
            return new NewPrayerDetailsResource(this);
        }

    }


}
