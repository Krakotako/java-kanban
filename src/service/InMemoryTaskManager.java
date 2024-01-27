package service;

import model.Task;
import model.Epic;
import model.Subtask;
import model.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private int counter;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Subtask> allSubtasks;
    private final ArrayList<Task> history;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        allSubtasks = new HashMap<>();
        history = new ArrayList<>();
    }

    private int generateId() {
        return ++counter;
    }

    @Override
    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpicsList() {
        return new ArrayList<>(this.epics.values());
    }

    @Override
    public ArrayList<Subtask> getAllSubtasksList() {
        return new ArrayList<>(this.allSubtasks.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasksListByEpic(Epic epic) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtasksIds()) {
                subtasksList.add(allSubtasks.get(subtaskId));
            }
        }
        return subtasksList;
    }

    @Override
    public Task addNewTask(Task task) {
        final int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        final int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        final int id = generateId();
        subtask.setId(id);
        allSubtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getSubtasksIds().add(id);
        updateEpicStatus(epic.getId());
        return subtask;
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        history.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        history.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = allSubtasks.get(id);
        history.add(subtask);
        return subtask;
    }

    @Override
    public void updateTask(Task task) {
        if (task == null) {
            return;
        }
        final Task savedTask = tasks.get(task.getId());
        if (savedTask == null) {
            return;
        }
        savedTask.setName(task.getName());
        savedTask.setDescription(task.getDescription());
        savedTask.setStatus(task.getStatus());
    }

    @Override
    public void updateEpic(final Epic epic) {
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

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            return;
        }
        final Subtask savedSubtask = allSubtasks.get(subtask.getId());
        if (savedSubtask == null) {
            return;
        }
        final Epic savedEpic = epics.get(subtask.getEpicId());
        if (savedEpic == null) {
            return;
        }
        savedSubtask.setName(subtask.getName());
        savedSubtask.setDescription(subtask.getDescription());
        savedSubtask.setStatus(subtask.getStatus());
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

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(int id) {
        ArrayList<Integer> subtasksIds = epics.get(id).getSubtasksIds();
        for (Integer subtaskId : subtasksIds) {
            allSubtasks.remove(subtaskId);
        }
        epics.remove(id);
    }
    
    @Override
    public void deleteSubtaskById(int id) {
        Epic epic = epics.get(allSubtasks.get(id).getEpicId());
        epic.getSubtasksIds().remove(Integer.valueOf(id));
        allSubtasks.remove(id);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        allSubtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            updateEpicStatus(epic.getId());
        }
        allSubtasks.clear();
    }
    @Override
    public List<Task> getHistory() {
        return history;
    }

    private void updateHistory() {
    }
}
