package task;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksIds = new ArrayList<>();

    public Epic(String name, String description, TaskStatus status) {
        super(name, description, status);
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Epic) || !(super.equals(obj))) {
            return false;
        } else {
            Epic otherEpic = (Epic) obj;
            return subtasksIds.equals(otherEpic.subtasksIds);
        }
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        if (subtasksIds != null) {
            hash += subtasksIds.hashCode();
        }
        return hash;
    }
}
