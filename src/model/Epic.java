package model;

import java.util.ArrayList;

public class Epic extends Task {

    private final ArrayList<Integer> subtasksIds;

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
        subtasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksIds() {
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
