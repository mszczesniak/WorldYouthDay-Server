package pl.kielce.tu.worldyouthday.scheduler;


import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EVENT_DETAILS")
public class EventDetails implements Serializable {

    @EmbeddedId
    private EventDetailsPK id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected EventDetails() {
    }

    public EventDetails(String eventId, Language language, String title, String content, Long version) {
        this.id = new EventDetailsPK(eventId, language);
        this.title = title;
        this.description = content;
        this.version = version;
    }

    public EventDetailsPK getId() {
        return id;
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

        EventDetails that = (EventDetails) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return !(version != null ? !version.equals(that.version) : that.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", version=" + version +
                '}';
    }
}
