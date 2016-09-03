package pl.kielce.tu.worldyouthday.version;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "VERSION")
public class Version implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name = "version_table", nullable = false)
    private VersionTable table;

    @Column(name = "version_value", nullable = false)
    private Long value;

    protected Version() {
    }

    public Version(VersionTable table, Long value) {
        this.table = table;
        this.value = value;
    }

    public VersionTable getTable() {
        return table;
    }

    public Long getValue() {
        return value;
    }
}
