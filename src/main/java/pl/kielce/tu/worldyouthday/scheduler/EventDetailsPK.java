package pl.kielce.tu.worldyouthday.scheduler;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class EventDetailsPK implements Serializable {

    @Column(name = "event_id")
    private String eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    public EventDetailsPK() {
    }

    public EventDetailsPK(String eventId, Language language) {
        this.eventId = eventId;
        this.language = language;
    }

    public String getEventId() {
        return eventId;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventDetailsPK that = (EventDetailsPK) o;

        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        return language == that.language;

    }

    @Override
    public int hashCode() {
        int result = eventId != null ? eventId.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EventDetailsPK{" +
                "eventId='" + eventId + '\'' +
                ", language=" + language +
                '}';
    }
}
