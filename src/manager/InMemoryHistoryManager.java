package manager;

import model.*;
import java.util.List;
import java.util.ArrayList;


public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history;

    public InMemoryHistoryManager() {
        history = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        Task taskToAdd;
        if (task instanceof Epic) {
            taskToAdd = new Epic(task);
        } else if (task instanceof Subtask) {
            taskToAdd = new Subtask(task);
        } else {
            taskToAdd = new Task(task);
        }
        history.add(taskToAdd);
        if (history.size() >= 11) {
            history.remove(0);
        }
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
