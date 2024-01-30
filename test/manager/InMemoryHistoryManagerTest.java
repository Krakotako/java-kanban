package manager;

import model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class InMemoryHistoryManagerTest {

    TaskManager taskManager = Managers.getDefaultManager();
    HistoryManager historyManager = Managers.getDefaultHistoryManager();

    @Test
    void addTest() {
        Task task = new Task(1,"a", "b", TaskStatus.NEW);
        Epic epic = new Epic(2, "a", "b", TaskStatus.NEW);
        Subtask subtask = new Subtask(3, "a", "b", TaskStatus.NEW, 4);
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "метод getHistory() не возвращает историю");
        assertNotEquals(0, history.size(), "История пуста");
        assertEquals(3, history.size(), "Задачи не добавляются в историю");
        assertEquals(task, history.get(0), "Обычная задача не правильно добавляется в историю");
        assertEquals(epic, history.get(1), "Эпик не добавляется в историю");
        assertEquals(subtask, history.get(2), "Подзадача не добавляется в историю");
    }

    @Test
    void shouldSaveThePreviousVersionOfTheTask() {
        Task firstTaskVersion = new Task("first", "firstVersion", TaskStatus.NEW);
        taskManager.addNewTask(firstTaskVersion);
        taskManager.getTaskById(firstTaskVersion.getId());
        Task secondTaskVersion  = new Task("second", "secondVersion", TaskStatus.DONE);
        taskManager.updateTask(secondTaskVersion);
        taskManager.getTaskById(firstTaskVersion.getId());
        List<Task> history = taskManager.getHistory();
        assertEquals(firstTaskVersion, history.get(0), "Старая версия задачи не сохраняется в истории");
    }
}
