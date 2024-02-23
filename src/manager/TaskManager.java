package manager;

import model.*;
import java.util.List;

public interface TaskManager {

    List<Task> getTasksList();

    List<Epic> getEpicsList();

    List<Subtask> getSubtasksList();

    List<Subtask> getSubtasksListByEpicId(long id);

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

    List<Task> getHistory();

}
