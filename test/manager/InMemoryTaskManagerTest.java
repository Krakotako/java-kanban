package manager;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {

    private Task task;
    private Epic epic;
    private Subtask subtask;
    private InMemoryTaskManager manager;

    @BeforeEach
    public void setup() {
        manager = new InMemoryTaskManager();
        task = new Task(1, "a", "b", TaskStatus.NEW);
        epic = new Epic(2,"a", "b", TaskStatus.NEW);
        subtask = new Subtask(3, "a", "b", TaskStatus.NEW, 2);
    }

    @Test
    public void shouldAddDifferentTypesAndFindThemById() {
        final Task addedTask = manager.addNewTask(task);
        final Epic addedEpic = manager.addNewEpic(epic);
        final Subtask addedSubtask = manager.addNewSubtask(subtask);

        final Task taskFromManager = manager.getTaskById(addedTask.getId());
        final Epic epicFromManager = manager.getEpicById(addedEpic.getId());
        final Subtask subtaskFromManager = manager.getSubtaskById(addedSubtask.getId());

        assertEquals(addedTask, taskFromManager, "Задачи не совпадают");
        assertEquals(addedEpic, epicFromManager, "Эпики не совпадают");
        assertEquals(addedSubtask, subtaskFromManager, "Подзадачи не совпадают");
    }

    @Test
    public void addNewTaskTest() {
        final Task addedTask = manager.addNewTask(task);
        final Task taskFromManager = manager.getTaskById(addedTask.getId());

        assertNotNull(taskFromManager, "Задача не добавляется в менеджер");
        assertEquals(task, taskFromManager, "Задача неправильно сохраняется в менеджере");
        assertNotNull(manager.getTasksList(), "Список задач не возвращается");
        assertEquals(1, manager.getTasksList().size(), "Неверное количество задач в списке");
        assertEquals(task, manager.getTasksList().get(0), "Задачи не совпадают.");
    }

    @Test
    public void shouldNotAddSubtaskWithNotExistingEpicId() {
        Subtask incorrectSubtask = new Subtask(4, "a", "b", TaskStatus.NEW, 1000);
        Subtask addedIncorrectSubtask = manager.addNewSubtask(incorrectSubtask);
        assertFalse(manager.getSubtasksList().contains(addedIncorrectSubtask));
    }

    @Test
    public void idShouldBeCreateOnlyInManager() {
        Task task = new Task(100, "a", "b", TaskStatus.NEW);
        manager.addNewTask(task);
        assertNull(manager.getTaskById(100), "Добавилась задачи с неверно заданым id");
        assertNotNull(manager.getTaskById(1), "При добавлении задачи генерация id не работает");
    }
}
