package model;

public class Subtask extends Task {

    private final long epicId;

    public Subtask(String name, String description, TaskStatus status, long epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public Subtask(long id, String name, String description, TaskStatus status, long epicId) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask(Task task) {
        super(task);
        Subtask subtask = (Subtask) task;
        this.epicId = subtask.id;
    }

    public long getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        String result = "Subtask{" + "id='" + id + '\''
                                   + ", name='" + name + '\''
                                   + ", description='" + description + '\''
                                   + ", status='" + status + '\''
                                   + ", epicId='" + epicId + '\'' + "}";
        return result;
    }
}
