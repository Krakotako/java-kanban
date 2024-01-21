package manager;

import task.Task;
import task.Epic;
import task.Subtask;
import task.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int seq = 0;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> allSubtasks;

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        allSubtasks = new HashMap<>();
    }

    private int generateId() {
        return ++seq;
    }

    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(this.tasks.values());
    }

    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(this.epics.values());
    }

    public ArrayList<Subtask> getAllSubtasksList() {
        return new ArrayList<>(this.allSubtasks.values());
    }

    public ArrayList<Subtask> getSubtasksListByEpic(Epic epic) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtasksIds()) {
                subtasksList.add(allSubtasks.get(subtaskId));
            }
        }
        return subtasksList;
    }

    public int addNewTask(Task task) {
        final int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    public int addNewEpic(Epic epic) {
        final int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        updateEpicStatus(id);
        return id;
    }

    public int addNewSubtask(Subtask subtask) {
        final int id = generateId();
        subtask.setId(id);
        allSubtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().add(id);
        updateEpicStatus(epic.getId());
        return id;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return allSubtasks.get(id);
    }

    public void updateTask(Task task) {
        if (task == null) {
            return;
        }
        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        if (epic == null) {
            return;
        }
        final Epic savedEpic = epics.get(epic.getId());
        if (savedEpic == null) {
            return;
        }
        savedEpic.setName(epic.getName());
        savedEpic.setDescription(epic.getDescription());
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            return;
        }
        final Subtask savedSubtask = allSubtasks.get(subtask.getId());
        if (savedSubtask == null) {
            return;
        }
        final Epic savedEpic = epics.get(savedSubtask.getEpicId());
        if (savedEpic == null) {
            return;
        }
        allSubtasks.put(subtask.getId(), subtask);
        updateEpicStatus(savedEpic.getId());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        ArrayList<Integer> subtasksIds = epic.getSubtasksIds();
        boolean isAllSubtasksNew = true;
        boolean isAllSubtasksDone = true;
        for (Integer subtasksId : subtasksIds) {
            Subtask subtask = allSubtasks.get(subtasksId);
            if (subtask.getStatus() != TaskStatus.NEW) {
                isAllSubtasksNew = false;
            }
            if (subtask.getStatus() != TaskStatus.DONE) {
                isAllSubtasksDone = false;
            }
        }

        if (subtasksIds.isEmpty() || isAllSubtasksNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (isAllSubtasksDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        ArrayList<Integer> subtasksIds = epics.get(id).getSubtasksIds();
        for (Integer subtaskId : subtasksIds) {
            allSubtasks.remove(subtaskId);
        }
        epics.remove(id);
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = allSubtasks.get(id);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().remove(Integer.valueOf(id));
        allSubtasks.remove(id);
        updateEpicStatus(epic.getId());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        allSubtasks.clear();
    }

    public void deleteAllSubtasks() {
        allSubtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
        }
    }
}
