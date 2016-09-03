package pl.kielce.tu.worldyouthday.prayer;


import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRAYER_DETAILS")
public class PrayerDetails implements Serializable {

    @EmbeddedId
    private PrayerDetailsPK id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    PrayerDetails() {
    }

    public PrayerDetails(String prayerId, Language language, String title, String content, Long version) {
        this.id = new PrayerDetailsPK(prayerId, language);
        this.title = title;
        this.content = content;
        this.version = version;
    }

    public PrayerDetailsPK getId() {
        return id;
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

        PrayerDetails that = (PrayerDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PrayerDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", version=" + version +
                '}';
    }
}
