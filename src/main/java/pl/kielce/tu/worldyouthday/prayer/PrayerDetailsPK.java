package pl.kielce.tu.worldyouthday.prayer;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class PrayerDetailsPK implements Serializable {

    @Column(name = "prayer_id", nullable = false)
    private String prayerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private Language language;

    public PrayerDetailsPK() {
    }

    public PrayerDetailsPK(String prayerId, Language language) {
        this.prayerId = prayerId;
        this.language = language;
    }

    public String getPrayerId() {
        return prayerId;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrayerDetailsPK that = (PrayerDetailsPK) o;

        if (prayerId != null ? !prayerId.equals(that.prayerId) : that.prayerId != null) return false;
        return language == that.language;

    }

    @Override
    public int hashCode() {
        int result = prayerId != null ? prayerId.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PrayerDetailsPK{" +
                "prayerId='" + prayerId + '\'' +
                ", language=" + language +
                '}';
    }
}
