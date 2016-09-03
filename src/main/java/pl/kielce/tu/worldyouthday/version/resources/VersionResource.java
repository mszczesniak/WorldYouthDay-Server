package pl.kielce.tu.worldyouthday.version.resources;

import pl.kielce.tu.worldyouthday.version.VersionTable;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class VersionResource implements Serializable {

    private final VersionTable table;
    private final Long value;

    public VersionResource(Builder builder) {
        this.table = builder.table;
        this.value = builder.value;
    }


    public VersionTable getTable() {
        return table;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionResource that = (VersionResource) o;

        if (table != that.table) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = table != null ? table.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VersionResource{" +
                "table=" + table +
                ", value=" + value +
                '}';
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        private VersionTable table;
        private Long value;


        public Builder withTable(VersionTable table) {
            this.table = table;
            return this;
        }

        public Builder withValue(Long value) {
            this.value = value;
            return this;
        }

        public VersionResource build() {
            return new VersionResource(this);
        }

    }


}
