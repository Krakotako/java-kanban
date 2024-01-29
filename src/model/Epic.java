package model;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Long> subtasksIds;

    public Epic(long id, String name, String description, TaskStatus status) {
        super(id, name, description, status);
        subtasksIds = new ArrayList<>();
    }

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
        subtasksIds = new ArrayList<>();
    }

    public Epic(Task task) {
        super(task);
        Epic epic = (Epic) task;
        this.subtasksIds = epic.subtasksIds;
    }

    public ArrayList<Long> getSubtasksIds() {
        return subtasksIds;
    }

    @Override
    public String toString() {
        String result = "Epic{" + "id='" + id + '\''
                                + ", name='" + name + '\''
                                + ", description='" + description + '\''
                                + ", status='" + status + '\''
                                + ", subtasksId='" + subtasksIds + '\'' + "}";
        return result;
    }
}
