package manager;

import model.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Node> history;
    private Node firstNode;
    private Node lastNode;

    public InMemoryHistoryManager() {
        history = new HashMap<>();
    }

    private static class Node {

        private Node prev;
        private final Task data;
        private Node next;

        public Node(Node prev, Task data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
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
        remove(taskToAdd.getId());
        linkLast(taskToAdd);
    }

    @Override
    public void remove(long id) {
        if (history.get(id) != null) {
            removeNode(history.get(id));
            history.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasksHistory = new ArrayList<>();
        Node currentNode = firstNode;
        while (currentNode != null) {
            tasksHistory.add(currentNode.data);
            currentNode = currentNode.next;
        }
        return tasksHistory;
    }

    private void linkLast(Task task) {
        Node newNode = new Node(null, task, null);
        if (history.isEmpty()) {
            firstNode = newNode;
        } else {
            lastNode.next = newNode;
            newNode.prev = lastNode;
        }
        lastNode = newNode;
        history.put(task.getId(), newNode);
    }

    private void removeNode(Node task) {
        if (history.size() == 1) {
            firstNode = null;
            lastNode = null;
            return;
        }

        Node prevNode = task.prev;
        Node nextNode = task.next;
        if (prevNode != null) {
            prevNode.next = nextNode;
        } else {
            firstNode = nextNode;
        }

        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            lastNode = prevNode;
        }
    }
}
