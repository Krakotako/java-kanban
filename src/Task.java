import java.util.Objects;

public class Task {

    protected String name;
    protected String description;
    protected int id;
    protected TaskStatus taskStatus;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        id = hashCode();
        taskStatus = TaskStatus.NEW;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else {
            Task otherTask = (Task) obj;
            return Objects.equals(name, otherTask.name) && Objects.equals(description, otherTask.description)
                    && (id == otherTask.id);
        }
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (name != null) {
            hash += name.hashCode();
        }
        hash *= 31;
        if (description != null) {
            hash += description.hashCode();
        }
        hash *= 31;
        return hash;
    }
}
