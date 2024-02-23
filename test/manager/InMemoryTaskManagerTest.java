package manager;

import model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;

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
        epic = new Epic(2, "a", "b", TaskStatus.NEW);
        subtask = new Subtask(3, "a", "b", TaskStatus.NEW, 2);
    }

    @Test
    public void shouldCorrectlyAddTaskToTheManager() {
        final Task addedTask = manager.addNewTask(task);
        final Task taskFromManagerById = manager.getTaskById(addedTask.getId());
        assertNotNull(taskFromManagerById, "Задача не добавляется в менеджер");
        assertEquals(task, taskFromManagerById, "Задача неправильно сохраняется в менеджере");
    }

    @Test
    public void shouldAddDifferentTypesAndFindThemById() {
        final Task addedTask = manager.addNewTask(task);
        final Epic addedEpic = manager.addNewEpic(epic);
        final Subtask addedSubtask = manager.addNewSubtask(subtask);

        final Task taskFromManagerById = manager.getTaskById(addedTask.getId());
        final Epic epicFromManagerById = manager.getEpicById(addedEpic.getId());
        final Subtask subtaskFromManagerById = manager.getSubtaskById(addedSubtask.getId());

        assertEquals(addedTask, taskFromManagerById, "Задачи не совпадают");
        assertEquals(addedEpic, epicFromManagerById, "Эпики не совпадают");
        assertEquals(addedSubtask, subtaskFromManagerById, "Подзадачи не совпадают");
    }

    @Test
    public void shouldAddCorrectlyToTheTasksList() {
        manager.addNewTask(task);
        assertNotNull(manager.getTasksList(), "Список задач не возвращается");
        assertEquals(1, manager.getTasksList().size(), "Неверное количество задач в списке");
        assertEquals(task, manager.getTasksList().get(0), "Задача неправильно сохраняется в списке");
    }

    @Test
    public void shouldNotAddSubtaskWithNotExistingEpicId() {
        Subtask incorrectSubtask = new Subtask(4, "a", "b", TaskStatus.NEW, 1000);
        Subtask addedIncorrectSubtask = manager.addNewSubtask(incorrectSubtask);
        assertFalse(manager.getSubtasksList().contains(addedIncorrectSubtask));
    }

    @Test
    public void idShouldBeCreatedOnlyInManager() {
        Task taskWithIncorrectId = new Task(10000, "a", "b", TaskStatus.NEW);
        manager.addNewTask(taskWithIncorrectId);
        assertNull(manager.getTaskById(10000), "Добавилась задача с неверно заданым id");
        assertNotNull(manager.getTaskById(1), "При добавлении задачи генерация id работает неправильно");
    }

    @Test
    public void epicShouldCorrectlyAddAndDeleteSubtasksIds() {
        Epic epic1 = manager.addNewEpic(epic);
        Subtask subtask1 = manager.addNewSubtask(subtask);
        Subtask subtask2 = manager.addNewSubtask(new Subtask("a", "b", TaskStatus.NEW, epic.getId()));
        Subtask subtask3 = manager.addNewSubtask(new Subtask("c", "d", TaskStatus.NEW, epic.getId()));
        List<Long> subtaskIds = epic1.getSubtasksIds();

        assertTrue((subtaskIds.contains(subtask1.getId()) && subtaskIds.contains(subtask2.getId())
                && subtaskIds.contains(subtask3.getId())), "Id подзадач неправильно сохраняются в эпике");

        manager.deleteAllSubtasks();

        assertFalse((subtaskIds.contains(subtask1.getId()) || subtaskIds.contains(subtask2.getId())
                || subtaskIds.contains(subtask3.getId())), "Id подзадач неправильно сохраняются в эпике");
    }
}
