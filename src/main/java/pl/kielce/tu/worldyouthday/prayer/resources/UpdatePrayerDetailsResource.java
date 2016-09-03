package pl.kielce.tu.worldyouthday.prayer.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class UpdatePrayerDetailsResource implements Serializable {

    private final String title;
    private final String content;
    private final Long version;

    @JsonCreator
    public UpdatePrayerDetailsResource(
            @JsonProperty("title") String title,
            @JsonProperty("content") String content,
            @JsonProperty("version") Long version) {

        this.title = title;
        this.content = content;
        this.version = version;
    }

    public UpdatePrayerDetailsResource(Builder builder) {
        this.title = builder.title;
        this.content = builder.content;
        this.version = builder.version;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdatePrayerDetailsResource that = (UpdatePrayerDetailsResource) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdatePrayerDetailsResource{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", version=" + version +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String title;
        private String content;
        private Long version;


        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withVersion(Long version) {
            this.version = version;
            return this;
        }

        public UpdatePrayerDetailsResource build() {
            return new UpdatePrayerDetailsResource(this);
        }

    }


}
