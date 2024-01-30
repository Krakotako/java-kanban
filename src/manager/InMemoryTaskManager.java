package manager;

import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private long counter;
    private final Map<Long, Task> tasks;
    private final Map<Long, Epic> epics;
    private final Map<Long, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistoryManager();
    }

    private long generateId() {
        return ++counter;
    }

    @Override
    public List<Task> getTasksList() {
        return new ArrayList<>(this.tasks.values());
    }

    @Override
    public List<Epic> getEpicsList() {
        return new ArrayList<>(this.epics.values());
    }

    @Override
    public List<Subtask> getSubtasksList() {
        return new ArrayList<>(this.subtasks.values());
    }

    @Override
    public List<Subtask> getSubtasksListByEpicId(long id) {
        List<Subtask> subtasksList = new ArrayList<>();
        Epic epic = epics.get(id);
        if (epic != null) {
            for (Long subtaskId : epic.getSubtasksIds()) {
                subtasksList.add(subtasks.get(subtaskId));
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
            subtasks.put(id, subtask);
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
        Subtask subtask = subtasks.get(id);
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

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            return;
        }
        final Subtask savedSubtask = subtasks.get(subtask.getId());
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
        List<Long> subtasksIds = epic.getSubtasksIds();
        boolean isAllSubtasksNew = true;
        boolean isAllSubtasksDone = true;
        for (Long subtasksId : subtasksIds) {
            Subtask subtask = subtasks.get(subtasksId);
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
        List<Long> subtasksIds = epics.get(id).getSubtasksIds();
        for (Long subtaskId : subtasksIds) {
            subtasks.remove(subtaskId);
        }
        epics.remove(id);
    }
    
    @Override
    public void deleteSubtaskById(long id) {
        Epic epic = epics.get(subtasks.get(id).getEpicId());
        epic.getSubtasksIds().remove(id);
        subtasks.remove(id);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasksIds().clear();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}
