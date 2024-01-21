package task;

import java.util.Objects;

public class Subtask extends Task {

    private int epicId;

    public Subtask(String name, String description, TaskStatus status, int epicId) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Subtask) || !(super.equals(obj))) {
            return false;
        } else {
            Subtask otherSubtask = (Subtask) obj;
            return epicId == otherSubtask.getEpicId();
        }
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash += Objects.hash(epicId);
        return hash;
    }
}
