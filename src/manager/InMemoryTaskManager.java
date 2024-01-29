package manager;

import model.*;
import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private long counter;
    private final HashMap<Long, Task> tasks;
    private final HashMap<Long, Epic> epics;
    private final HashMap<Long, Subtask> allSubtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        allSubtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistoryManager();
    }

    private long generateId() {
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
    public ArrayList<Subtask> getSubtasksListByEpicId(long id) {
        ArrayList<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Long subtaskId : epic.getSubtasksIds()) {
                subtasksList.add(allSubtasks.get(subtaskId));
            }
        }
        return subtasksList;
    }

    @Override
    public Task addNewTask(Task task) {
        final long id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        final long id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            final long id = generateId();
            subtask.setId(id);
            allSubtasks.put(id, subtask);
            epic.getSubtasksIds().add(id);
            updateEpicStatus(epic.getId());
        }
        return subtask;
    }

    @Override
    public Task getTaskById(long id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public Epic getEpicById(long id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public Subtask getSubtaskById(long id) {
        Subtask subtask = allSubtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
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
        final Epic savedEpic = epics.get(savedSubtask.getEpicId());
        savedSubtask.setName(subtask.getName());
        savedSubtask.setDescription(subtask.getDescription());
        savedSubtask.setStatus(subtask.getStatus());
        updateEpicStatus(savedEpic.getId());
    }

    private void updateEpicStatus(long epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }
        ArrayList<Long> subtasksIds = epic.getSubtasksIds();
        boolean isAllSubtasksNew = true;
        boolean isAllSubtasksDone = true;
        for (Long subtasksId : subtasksIds) {
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
    public void deleteTaskById(long id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(long id) {
        ArrayList<Long> subtasksIds = epics.get(id).getSubtasksIds();
        for (Long subtaskId : subtasksIds) {
            allSubtasks.remove(subtaskId);
        }
        epics.remove(id);
    }
    
    @Override
    public void deleteSubtaskById(long id) {
        Epic epic = epics.get(allSubtasks.get(id).getEpicId());
        epic.getSubtasksIds().remove(id);
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
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }
}
