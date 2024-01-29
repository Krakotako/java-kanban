package manager;

import model.*;
import java.util.ArrayList;

public interface TaskManager {

    ArrayList<Task> getTasksList();

    ArrayList<Epic> getEpicsList();

    ArrayList<Subtask> getAllSubtasksList();

    ArrayList<Subtask> getSubtasksListByEpicId(long id);

    Task addNewTask(Task task);

    Epic addNewEpic(Epic epic);

    Subtask addNewSubtask(Subtask subtask);

    Task getTaskById(long id);

    Epic getEpicById(long id);

    Subtask getSubtaskById(long id);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void deleteTaskById(long id);

    void deleteEpicById(long id);

    void deleteSubtaskById(long id);

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    ArrayList<Task> getHistory();
}
