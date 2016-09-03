package pl.kielce.tu.worldyouthday.todo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TODO")
public class Todo implements Serializable {

    @Column(name = "TABLE_NAME", nullable = false)
    private String name;

    @Column(name = "ROW_DESC", nullable = false)
    private String description;

    @Column(name = "STATUS", nullable = false)
    private String status;

    protected Todo() {
    }

    public Todo(String name, String description, String status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (name != null ? !name.equals(todo.name) : todo.name != null) return false;
        if (description != null ? !description.equals(todo.description) : todo.description != null) return false;
        return status != null ? status.equals(todo.status) : todo.status == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
