import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    HashMap<Integer, Task> tasks;
    HashMap<Integer, Epic> epics;
    HashMap<Integer, Subtask> allSubtasks;

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        allSubtasks = new HashMap<>();
    }

    ArrayList<Task> getTasksList() {
        ArrayList<Task> tasksList = new ArrayList<>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        }
        return tasksList;
    }

    ArrayList<Epic> getEpicsList() {
        ArrayList<Epic> epicsList = new ArrayList<>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        }
        return epicsList;
    }

    ArrayList<Subtask> getAllSubtasksList() {
        ArrayList<Subtask> allSubtasksList = new ArrayList<>();
        for (Subtask subtask : allSubtasks.values()) {
            allSubtasksList.add(subtask);
        }
        return allSubtasksList;
    }

    ArrayList<Subtask> getSubtasksListByEpic(Epic epic) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        if (epic != null) {
            for (Subtask subtask : epic.subtasks) {
                subtasksList.add(subtask);
            }
        }
        return subtasksList;
    }

    void createTask(Task newTask) {
        if (newTask != null) {
            tasks.put(newTask.id, newTask);
        }
    }

    void createEpic(Epic newEpic) {
        if (newEpic != null && (epics.get(newEpic.id) == null)) {
            epics.put(newEpic.id, newEpic);
        }
    }

    void createSubtask(Epic epic, Subtask newSubtask) {
        if (newSubtask != null && epic != null) {
            allSubtasks.put(newSubtask.id, newSubtask);
            epic.subtasks.add(newSubtask);
        }
    }

    Task getTaskById(int id) {
        return tasks.get(id);
    }

    Epic getEpicById(int id) {
        return epics.get(id);
    }

    Subtask getSubtaskById(int id) {
        return allSubtasks.get(id);
    }

    void updateTask(Task task) {
        if (task != null) {
            if (tasks.containsKey(task.id)) {
                tasks.put(task.id, task);
            }
        }
    }

    private void updateEpic(Epic epic) {
        boolean isAllSubtasksDone = true;
        boolean isAllSubtaskNew = true;
        for (Subtask subtask : epic.subtasks) {
            if (subtask.taskStatus != TaskStatus.NEW) {
                isAllSubtaskNew = false;
            }
            if (subtask.taskStatus != TaskStatus.DONE) {
                isAllSubtasksDone = false;
            }
        }

        if (isAllSubtasksDone && !epic.subtasks.isEmpty()) {
            epic.taskStatus = TaskStatus.DONE;
        } else if (isAllSubtaskNew) {
            epic.taskStatus = TaskStatus.NEW;
        } else {
            epic.taskStatus = TaskStatus.IN_PROGRESS;
        }
    }

    void updateSubtask(Subtask subtask) {
        if (subtask != null) {
            if (allSubtasks.containsKey(subtask.id)) {
                Epic epic = epics.get(subtask.epicId);
                epic.subtasks.remove(allSubtasks.get(subtask.id));
                allSubtasks.put(subtask.id, subtask);
                epic.subtasks.add(subtask);
                updateEpic(epic);
            }
        }
    }

    void deleteTaskById(int id) {
        tasks.remove(id);
    }

    void deleteEpicById(int id) {
        epics.remove(id);
    }

    void deleteSubtaskById(int id) {
        if (allSubtasks.get(id) != null) {
            Epic epic = epics.get(allSubtasks.get(id).epicId);
            epic.subtasks.remove(allSubtasks.get(id));
            allSubtasks.remove(id);
        }
    }

    void deleteAllTasks() {
        tasks.clear();
    }

    void deleteAllEpics() {
        epics.clear();
        allSubtasks.clear();
    }

    void deleteAllSubtasks() {
        allSubtasks.clear();
        for (Epic epic : epics.values()) {
            epic.taskStatus = TaskStatus.NEW;
            epic.subtasks.clear();
        }
    }
}
