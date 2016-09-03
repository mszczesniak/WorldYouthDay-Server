package pl.kielce.tu.worldyouthday.prayer.resources;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class PrayerDetailsResource implements Serializable {

    private final String title;
    private final String content;
    private final Long version;

    public PrayerDetailsResource(Builder builder) {
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

        PrayerDetailsResource that = (PrayerDetailsResource) o;

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
        return "PrayerDetailsResource{" +
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

        public PrayerDetailsResource build() {
            return new PrayerDetailsResource(this);
        }

    }


}
