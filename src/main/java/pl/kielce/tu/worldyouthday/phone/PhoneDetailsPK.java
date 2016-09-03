package pl.kielce.tu.worldyouthday.phone;

import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Embeddable
public class PhoneDetailsPK implements Serializable {

    @Column(name = "PHD_ID")
    private String id;

    @Column(name = "PHD_LANGUAGE")
    @Enumerated(EnumType.STRING)
    private Language language;

    public PhoneDetailsPK() {
    }

    public PhoneDetailsPK(String id, Language language) {
        this.id = id;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public Language getLanguage() {
        return language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneDetailsPK that = (PhoneDetailsPK) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return language == that.language;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhoneDetailsPK{" +
                "id='" + id + '\'' +
                ", language=" + language +
                '}';
    }
}
