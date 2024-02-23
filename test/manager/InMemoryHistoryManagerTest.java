package manager;

import model.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {

    TaskManager taskManager;
    HistoryManager historyManager;

    @BeforeEach
    public void setup() {
        taskManager = Managers.getDefaultManager();
        historyManager = Managers.getDefaultHistoryManager();
    }

    @Test
    public void shouldCorrectlyAddTasksToTheHistory() {
        historyManager.add(new Task(1, "a", "b", TaskStatus.NEW));
        assertNotNull(historyManager.getHistory(), "Историяметод getHistory() не возвращает историю");
        assertFalse(historyManager.getHistory().isEmpty(), "История просмотра пуста");
        assertEquals(1, historyManager.getHistory().size(), "Первая задача не сохраняется");

        historyManager.add(new Task(2, "c", "d", TaskStatus.NEW));
        historyManager.add(new Task(3, "e", "f", TaskStatus.NEW));
        historyManager.add(new Task(4, "g", "h", TaskStatus.NEW));
        assertEquals(4, historyManager.getHistory().size(), "Несколько задач не добавляется в историю");
    }

    @Test
    public void shouldAddDifferentTypesOfTasksToTheHistory() {
        Task task = new Task(1,"a", "b", TaskStatus.NEW);
        Epic epic = new Epic(2, "a", "b", TaskStatus.NEW);
        Subtask subtask = new Subtask(3, "a", "b", TaskStatus.NEW, 4);
        historyManager.add(task);
        historyManager.add(epic);
        historyManager.add(subtask);

        final List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size(), "Задачи не добавляются в историю");
        assertEquals(task, history.get(0), "Обычная задача неправильно добавляется в историю");
        assertEquals(epic, history.get(1), "Эпик неправильно добавляется в историю");
        assertEquals(subtask, history.get(2), "Подзадача неправильно добавляется в историю");
    }

    @Test
    public void shouldCorrectlyRemoveAndAddNewTaskVersion() {
        Task firstVersion = new Task(5,"first", "firstVer", TaskStatus.NEW);
        Task secondVersion = new Task(5,"second", "secondVer", TaskStatus.NEW);
        historyManager.add(firstVersion);
        historyManager.add(secondVersion);
        assertNotEquals(2, historyManager.getHistory().size(), "Задача добавляется, а не заменяется");
        assertEquals(1, historyManager.getHistory().size(), "В истории должна быть одна задача");
        assertEquals(secondVersion, historyManager.getHistory().get(0), "В истории не та версия задачи");
    }
}
