package pl.kielce.tu.worldyouthday.prayer;


import pl.kielce.tu.worldyouthday.language.Language;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "PRAYER")
public class Prayer implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prayer_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapKey(name = "id.language")
    private Map<Language, PrayerDetails> details = new HashMap<>();

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    protected Prayer() {
    }

    public Prayer(String id, Map<Language, PrayerDetails> details, Long version) {
        this.id = id;
        this.details = details;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public Long getVersion() {
        return version;
    }

    public Map<Language, PrayerDetails> getDetails() {
        return details;
    }

    public PrayerDetails getDetails(Language language) {
        return details.getOrDefault(language, details.get(Language.getDefault()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Prayer prayer = (Prayer) o;

        if (id != null ? !id.equals(prayer.id) : prayer.id != null) return false;
        if (version != null ? !version.equals(prayer.version) : prayer.version != null) return false;
        return !(details != null ? !details.equals(prayer.details) : prayer.details != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Prayer{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", details=" + details +
                '}';
    }
}
